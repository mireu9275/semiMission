package kr.eme.semiMission.listeners

import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import kr.eme.semiMission.api.events.MissionEvent
import kr.eme.semiMission.utils.SoundUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object MissionProgressListener : Listener {

    @EventHandler
    fun onMissionEvent(e: MissionEvent) {
        val version = e.version
        val curIndex = MissionStateManager.getCurrentIndex(version)
        if (curIndex == -1) return // 아직 첫 미션 수락 안됨

        val mission = MissionManager.getCurrent(version, curIndex) ?: return
        val cond = mission.condition

        if (cond.type == e.type &&
            cond.target == e.target &&
            e.value >= cond.need
        ) {
            if (MissionStateManager.advanceIf(version, mission.id)) {
                Bukkit.broadcastMessage("§a[${version.name}] 미션 완료: ${mission.title}")
                Bukkit.broadcastMessage("§b보상을 수령하려면 미션 창에서 클릭하세요.")

                Bukkit.getOnlinePlayers().forEach { p ->
                    SoundUtil.missionClear(p)
                }

                val nextIndex = MissionStateManager.getCurrentIndex(version)
                if (nextIndex <= MissionManager.lastIndex(version)) {
                    val next = MissionManager.getCurrent(version, nextIndex)
                    if (next != null) {
                        Bukkit.broadcastMessage("§e다음 미션이 해금되었습니다: ${next.title}")
                    } else {
                        Bukkit.broadcastMessage("§6[${version.name}] 모든 미션을 완료했습니다!")
                    }
                }
            }
        }
    }

}
