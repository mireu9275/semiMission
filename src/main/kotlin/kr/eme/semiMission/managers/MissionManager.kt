package kr.eme.semiMission.managers

import kr.eme.semiMission.objects.model.Condition
import kr.eme.semiMission.objects.model.Mission
import kr.eme.semiMission.objects.model.Reward

object MissionManager {
    val missions: List<Mission> = listOf(
        Mission(
            1,
            "기본 아이템 받기",
            "홈 모듈에서 기본 아이템을 지급받으세요.",
            Condition("DEVICE_INTERACTION", "home_module",1),
            Reward(ep = 500)
        )
        // 이런식으로 이어서 작성하기!
    )
    fun currentIndexOf(id: Int) = missions.indexOfFirst { it.id == id }
    fun getCurrent(index: Int) = missions.getOrNull(index)
    fun firstMissionId(): Int = missions.first().id
    fun lastIndex(): Int = missions.lastIndex
}