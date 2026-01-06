package kr.eme.prcMission.commands

import kr.eme.prcMission.enums.MissionVersion
import kr.eme.prcMission.managers.MissionManager
import kr.eme.prcMission.managers.MissionStateManager
import kr.eme.prcMission.objects.guis.MissionInitGUI
import kr.eme.prcMission.objects.items.ItemTemplates
import kr.eme.prcMission.objects.models.ItemReward
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import kotlin.reflect.full.memberProperties

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
            "testitem"  -> testItem(sender, args.getOrNull(1))
            else        -> usage(sender)
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (!sender.isOp) {
            return mutableListOf()
        }

        if (args.size == 1) {
            val base = listOf("open", "reload", "complete", "clear", "debug", "testitem")
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

        // 여기 추가: /mission testItem <아이템명> 자동완성
        if (args.size == 2 && args[0].equals("testitem", ignoreCase = true)) {
            return kr.eme.prcMission.objects.items.ItemTemplates::class.memberProperties
                .map { it.name }
                .filter { it.startsWith(args[1], ignoreCase = true) }
                .toMutableList()
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

        kr.eme.prcMission.objects.guis.MissionPageGUI(player, version, 1).apply {
            setFirstGUI(); open()
        }
        return true
    }

    private fun reloadState(sender: CommandSender): Boolean {
        MissionStateManager.load()
        sender.sendMessage("§a미션 플러그인이 리로드 되었습니다.")
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

    private fun testItem(sender: CommandSender, name: String?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c플레이어만 사용할 수 있습니다.")
            return true
        }
        if (name == null) {
            sender.sendMessage("§c사용법: /mission testItem <아이템변수명>")
            return true
        }

        // ItemTemplates 안의 val 변수 중에서 이름으로 찾기
        val reward = ItemTemplates::class.memberProperties
            .firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?.getter
            ?.call(ItemTemplates) as? ItemReward

        if (reward == null) {
            sender.sendMessage("§c존재하지 않는 아이템: $name")
        } else {
            val item = reward.toItemStack()
            sender.inventory.addItem(item)
            sender.sendMessage("§a${reward.name} 지급 완료!")
        }

        return true
    }
}
