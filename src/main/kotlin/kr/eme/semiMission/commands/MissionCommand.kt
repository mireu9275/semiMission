package kr.eme.semiMission.commands

import kr.eme.semiMission.enums.MissionVersion
import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import kr.eme.semiMission.objects.guis.MissionInitGUI
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object MissionCommand : TabExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) return openGuiOrTell(sender)

        val sub = args[0].lowercase()

        if (sender !is Player && sub in setOf("open", "clear", "complete")) {
            sender.sendMessage("§c이 명령어는 플레이어만 사용할 수 있습니다.")
            return true
        }

        return when (sub) {
            "open"      -> openGui(sender as Player, args.getOrNull(1))
            "reload"    -> reloadState(sender)
            "complete"  -> completeMission(sender, args.getOrNull(1), args.getOrNull(2))
            "clear"     -> clearState(sender, args.getOrNull(1))
            "debug"     -> debugItemNBT(sender)
            else        -> usage(sender)
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 1) {
            val base = listOf("open", "reload", "complete", "clear", "debug")
            return base.filter { it.startsWith(args[0].lowercase()) }.toMutableList()
        }

        if (args.size == 2 && args[0].equals("complete", ignoreCase = true)) {
            return MissionVersion.entries.map { it.name.lowercase() }.toMutableList()
        }

        if (args.size == 3 && args[0].equals("complete", ignoreCase = true)) {
            val version = runCatching { MissionVersion.valueOf(args[1].uppercase()) }.getOrNull()
            if (version != null) {
                return MissionManager.getMissions(version).map { it.id.toString() }.toMutableList()
            }
        }

        return mutableListOf()
    }

    // ===== helpers =====
    private fun usage(sender: CommandSender): Boolean {
        sender.sendMessage("§e사용법: /mission [open|reload|complete|clear|debug]")
        sender.sendMessage("§7 - /mission open [v1|v2]")
        sender.sendMessage("§7 - /mission complete <v1|v2> [id]")
        sender.sendMessage("§7 - /mission clear [v1|v2|all]")
        return true
    }

    private fun openGuiOrTell(sender: CommandSender): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c플레이어만 사용할 수 있습니다.")
            return true
        }
        return openGui(sender, null)
    }

    private fun openGui(player: Player, versionArg: String?): Boolean {
        if (versionArg == null) {
            MissionInitGUI(player).apply { setFirstGUI(); open() }
            return true
        }

        val version = runCatching { MissionVersion.valueOf(versionArg.uppercase()) }.getOrNull()
        if (version == null) {
            player.sendMessage("§c존재하지 않는 버전입니다. (예: v1, v2)")
            return true
        }

        kr.eme.semiMission.objects.guis.MissionPageGUI(player, version, 1).apply {
            setFirstGUI(); open()
        }
        return true
    }

    private fun reloadState(sender: CommandSender): Boolean {
        MissionStateManager.save()
        sender.sendMessage("§a미션 상태가 저장/갱신되었습니다.")
        return true
    }

    private fun completeMission(sender: CommandSender, versionArg: String?, idArg: String?): Boolean {
        if (sender is Player && !sender.isOp) {
            sender.sendMessage("§c권한이 없습니다.")
            return true
        }

        val version = runCatching { MissionVersion.valueOf(versionArg?.uppercase() ?: "") }.getOrNull()
        if (version == null) {
            sender.sendMessage("§c버전을 지정해야 합니다. (예: v1, v2)")
            return true
        }

        val targetId = idArg?.toIntOrNull()
        if (targetId != null) {
            val idx = MissionManager.currentIndexOf(version, targetId)
            if (idx == -1) {
                sender.sendMessage("§c존재하지 않는 미션 ID입니다.")
                return true
            }
            MissionStateManager.advanceIf(version, targetId)
            sender.sendMessage("§a[${version.name}] 미션(ID=$targetId) 강제 진행됨. (현재 인덱스: ${MissionStateManager.getCurrentIndex(version)})")
            return true
        }

        val curIdx = MissionStateManager.getCurrentIndex(version)
        val mission = MissionManager.getCurrent(version, curIdx)
        if (mission == null) {
            sender.sendMessage("§c현재 진행 중인 미션이 없습니다.")
            return true
        }

        MissionStateManager.advanceIf(version, mission.id)
        sender.sendMessage("§a[${version.name}] 미션 강제 진행됨. (현재 인덱스: ${MissionStateManager.getCurrentIndex(version)})")
        return true
    }

    private fun clearState(sender: CommandSender, versionArg: String?): Boolean {
        if (sender is Player && !sender.isOp) {
            sender.sendMessage("§c권한이 없습니다.")
            return true
        }

        if (versionArg == null || versionArg.equals("all", ignoreCase = true)) {
            MissionStateManager.resetAll()
            sender.sendMessage("§e모든 버전의 미션 상태가 초기화되었습니다.")
            return true
        }

        val version = runCatching { MissionVersion.valueOf(versionArg.uppercase()) }.getOrNull()
        if (version == null) {
            sender.sendMessage("§c존재하지 않는 버전입니다. (예: v1, v2, all)")
            return true
        }

        MissionStateManager.reset(version)
        sender.sendMessage("§e[${version.name}] 미션 상태가 초기화되었습니다.")
        return true
    }

    private fun debugItemNBT(sender: CommandSender): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c플레이어만 사용할 수 있습니다.")
            return false
        }

        val item = sender.inventory.itemInMainHand
        if (item.type.isAir) {
            sender.sendMessage("§c손에 아이템을 들고 있지 않습니다.")
            return false
        }

        val serialized = item.serialize()
        sender.sendMessage("§e[아이템 전체 NBT/직렬화 정보]")
        serialized.forEach { (k, v) -> sender.sendMessage("§7$k: $v") }

        return true
    }
}
