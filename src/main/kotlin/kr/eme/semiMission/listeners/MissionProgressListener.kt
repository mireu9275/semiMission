package kr.eme.semiMission.listeners

import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import kr.eme.semiMission.managers.RewardManager
import kr.eme.semiMission.objects.events.MissionEvent
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

        // ✅ 조건 매칭 (need 기준으로 수정됨)
        if (cond.type == e.type &&
            cond.target == e.target &&
            e.value >= cond.need
        ) {
            // 조건 충족 → 완료 처리
            if (MissionStateManager.advanceIf(mission.id)) {
                MissionStateManager.save()
                Bukkit.broadcastMessage("§a[미션 완료] ${mission.title}")

                // ✅ 보상 지급
                RewardManager.give(mission, e.player.name)

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
