package kr.eme.semiMission.commands

import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import kr.eme.semiMission.managers.GUIManager
import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object MissionCommand : TabExecutor {

    // ===== Command =====
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) return openGuiOrTell(sender)

        val sub = args[0].lowercase()

        // 플레이어 전용 서브커맨드 가드
        if (sender !is Player && sub in setOf("open", "previous")) {
            sender.sendMessage("§c해당 명령어는 플레이어만 사용할 수 있습니다.")
            return true
        }

        return when (sub) {
            "open"      -> openGui(sender as Player)
            "prev",
            "reload"    -> reloadState(sender)
            "complete"  -> completeMission(sender, args.getOrNull(1))
            "clear"     -> clearState(sender)
            "debug"     -> debugItemNBT(sender)
            else        -> usage(sender)
        }
    }

    // ===== Tab Complete =====
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        // 1번째 인자: 서브커맨드 추천
        if (args.size == 1) {
            val base = listOf("open", "reload", "complete")
            return base.filter { it.startsWith(args[0].lowercase()) }.toMutableList()
        }

        // 2번째 인자: complete <id> 에서 미션 ID 추천
        if (args.size == 2 && args[0].equals("complete", ignoreCase = true)) {
            val ids = MissionManager.missions.map { it.id.toString() }
            return ids.filter { it.startsWith(args[1]) }.toMutableList()
        }

        return mutableListOf()
    }

    // ===== helpers =====
    private fun usage(sender: CommandSender): Boolean {
        sender.sendMessage("§e사용법: /mission [open|next|prev|reload|complete]")
        return true
    }

    private fun openGuiOrTell(sender: CommandSender): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c플레이어만 사용할 수 있습니다.")
            return true
        }
        return openGui(sender)
    }

    private fun openGui(player: Player): Boolean {
        val gui = kr.eme.semiMission.objects.guis.MissionPageGUI(player, 1)
        gui.setFirstGUI()
        gui.open()
        return true
    }

    private fun reloadState(sender: CommandSender): Boolean {
        MissionStateManager.save()
        sender.sendMessage("§a상태가 저장되었습니다.")
        return true
    }

    private fun completeMission(
        sender: CommandSender,
        idArg: String?
    ): Boolean {
        if (sender is Player && !sender.isOp) {
            sender.sendMessage("§c권한이 없습니다.")
            return true
        }

        // 숫자 인자 있으면 그 ID로 강제 진행, 없으면 현재 미션 진행
        val targetId = idArg?.toIntOrNull()
        if (targetId != null) {
            // 현재 인덱스가 이 ID가 아닐 때는 인덱스 맞춰서 강제 진행
            val idx = MissionManager.currentIndexOf(targetId)
            if (idx == -1) {
                sender.sendMessage("§c존재하지 않는 미션 ID입니다.")
                return true
            }
            // 현재 인덱스와 목표 ID가 동일할 때만 advanceIf가 true -> 보상 처리 등은 ProgressListener에서 함
            MissionStateManager.advanceIf(targetId)
            sender.sendMessage("§a미션(ID=$targetId)이 강제로 진행되었습니다. (현재 인덱스: ${MissionStateManager.getCurrentIndex()})")
            return true
        }

        val curIdx = MissionStateManager.getCurrentIndex()
        val mission = MissionManager.getCurrent(curIdx)
        if (mission == null) {
            sender.sendMessage("§c현재 진행 중인 미션이 없습니다.")
            return true
        }

        MissionStateManager.advanceIf(mission.id)
        sender.sendMessage("§a미션이 강제로 진행되었습니다. (현재 인덱스: ${MissionStateManager.getCurrentIndex()})")
        return true
    }
    private fun clearState(sender: CommandSender): Boolean {
        if (sender is Player && !sender.isOp) {
            sender.sendMessage("§c권한이 없습니다.")
            return true
        }

        // 초기화
        MissionStateManager.reset()
        MissionStateManager.save()

        sender.sendMessage("§e미션 상태가 초기화되었습니다.")
        return true
    }

    private fun debugItemNBT(sender: CommandSender): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c플레이어만 사용할 수 있습니다.")
            return true
        }

        val item = sender.inventory.itemInMainHand
        if (item.type.isAir) {
            sender.sendMessage("§c손에 아이템을 들고 있지 않습니다.")
            return true
        }

        // Bukkit serialize() → Map<String, Any> 로 변환
        val serialized = item.serialize()
        sender.sendMessage("§e[아이템 전체 NBT/직렬화 정보]")
        serialized.forEach { (k, v) ->
            sender.sendMessage("§7$k: $v")
        }

        return true
    }


}
