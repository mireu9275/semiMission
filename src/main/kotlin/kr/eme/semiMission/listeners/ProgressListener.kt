package kr.eme.semiMission.listeners

import kr.eme.semiMission.api.events.ModuleActionEvent
import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import kr.eme.semiMission.managers.RewardManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object ProgressListener : Listener {
    private var currentProgress = 0 // 진행 중인 미션의 누적치만 관리함.

    @EventHandler
    fun onModuleAction(e: ModuleActionEvent) {
        val player = e.player
        val index = MissionStateManager.getCurrentIndex()
        val mission = MissionManager.getCurrent(index) ?: return

        val condition = mission.condition
        if (condition.type != e.action) return
        if (!condition.target.equals(e.detail, ignoreCase = true)) return

        currentProgress += e.amount
        if (currentProgress >= condition.need) {
            MissionStateManager.advanceIf(mission.id)
            RewardManager.give(mission, player.name)
            player.server.broadcastMessage("§a[미션 완료] §f${mission.title}")
            currentProgress = 0
        }
    }
    fun resetProgress() { currentProgress = 0 }
}