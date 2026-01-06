package kr.eme.prcMission.listeners

import kr.eme.prcMission.api.events.MissionEvent
import kr.eme.prcMission.enums.MissionVersion
import kr.eme.prcMission.managers.MissionManager
import kr.eme.prcMission.managers.MissionStateManager
import kr.eme.prcMission.utils.SoundUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object MissionProgressListener : Listener {

    @EventHandler
    fun onMissionEvent(e: MissionEvent) {
        val version = e.version

        // 아직 해당 버전의 미션이 시작되지 않았다면 무시 (-1은 미수락 상태)
        val curIndexBefore = MissionStateManager.getCurrentIndex(version)
        if (curIndexBefore == -1) return

        // 현재 진행 중인 미션
        val currentMission = MissionManager.getCurrent(version, curIndexBefore) ?: return

        // 현재 미션의 조건(type/target)과 이벤트가 일치할 때만 진행도 반영
        val cond = currentMission.condition
        if (cond.type != e.type || cond.target != e.target) return

        val progress = MissionStateManager.getProgress(version, currentMission.id)

        // ✅ 체크리스트형: 이미 완료된 행위라면 무시
        if (cond.goal == null && progress.completedConditions.contains(e.value)) {
            return
        }

        // ✅ 누적형/체크리스트형 공통 처리
        MissionStateManager.addProgress(version, currentMission, e.value)

        // 완료 여부는 인덱스 증가로 판정 (completeMission에서 인덱스+저장 처리)
        val curIndexAfter = MissionStateManager.getCurrentIndex(version)

        if (curIndexAfter > curIndexBefore) {
            // ====== 미션 완료 시점 ======
            SoundUtil.missionClear(e.player)
            Bukkit.broadcastMessage("§a[${version.name}] §f미션 완료: §e${currentMission.title}")

            val lastIndex = MissionManager.lastIndex(version)

            if (curIndexAfter <= lastIndex) {
                // 아직 같은 버전 내의 다음 미션 있음
                val next = MissionManager.getCurrent(version, curIndexAfter)
                if (next != null) {
                    Bukkit.broadcastMessage("§e다음 미션이 해금되었습니다: ${next.title}")
                }
            } else {
                // 현재 버전의 모든 미션 완료
                Bukkit.broadcastMessage("§6[${version.name}] 모든 미션을 완료했습니다!")

                // ✅ 다음 버전 해금 안내
                val allVersions = MissionVersion.entries
                val idx = allVersions.indexOf(version)
                if (idx != -1 && idx + 1 < allVersions.size) {
                    val nextVersion = allVersions[idx + 1]
                    if (MissionStateManager.canStart(nextVersion)) {
                        Bukkit.broadcastMessage("§b다음 버전(${nextVersion.name}) 미션이 해금되었습니다!")
                    }
                }
            }
        } else {
            // ====== 아직 완료 전 (진행 중) ======
            if (cond.goal != null) {
                // 누적형 중간 피드백
                Bukkit.broadcastMessage(
                    "§7[${version.name}] ${currentMission.title} 진행도: ${progress.progressCount} / ${cond.goal}"
                )
            } else {
                // 체크리스트형 중간 피드백 (방금 완료된 행위 표시)
                val justCompleted = e.value
                if (progress.completedConditions.contains(justCompleted)) {
                    val desc = cond.descriptions[justCompleted] ?: "$justCompleted"
                    Bukkit.broadcastMessage("§7[${version.name}] $desc §a완료!")
                }
            }
        }
    }
}
