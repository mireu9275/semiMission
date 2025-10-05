package kr.eme.semiMission.objects.models

data class MissionProgress(
    val missionID: String,
    val completedConditions: MutableSet<Int> = mutableSetOf(),
    var progressCount: Int = 0   // 누적형 미션 카운트
)
