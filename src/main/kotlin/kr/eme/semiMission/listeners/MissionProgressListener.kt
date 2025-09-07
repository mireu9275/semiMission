package kr.eme.semiMission.listeners

import io.papermc.paper.command.brigadier.argument.ArgumentTypes.player
import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import kr.eme.semiMission.objects.events.MissionEvent
import kr.eme.semiMission.utils.SoundUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object MissionProgressListener : Listener {

    @EventHandler
    fun onMissionEvent(e: MissionEvent) {
        val curIndex = MissionStateManager.getCurrentIndex()
        if (curIndex == -1) return // 아직 첫 미션 수락 안됨

        val mission = MissionManager.getCurrent(curIndex) ?: return
        val cond = mission.condition

        // 조건 매칭
        if (cond.type == e.type &&
            cond.target == e.target &&
            e.value >= cond.need
        ) {
            // 완료 처리 (보상 지급 X)
            if (MissionStateManager.advanceIf(mission.id)) {
                MissionStateManager.save()
                Bukkit.broadcastMessage("§a[미션 완료] ${mission.title}")
                Bukkit.broadcastMessage("§b보상을 수령하려면 미션 창에서 클릭하세요.")

                Bukkit.getOnlinePlayers().forEach { p ->
                    SoundUtil.missionClear(p)
                }

                val nextIndex = MissionStateManager.getCurrentIndex()
                if (nextIndex <= MissionManager.lastIndex()) {
                    val next = MissionManager.getCurrent(nextIndex)
                    if (next != null) {
                        Bukkit.broadcastMessage("§e다음 미션이 해금되었습니다: ${next.title}")
                    }
                }
            }
        }
    }
}
