package kr.eme.prcMission.objects.models

data class Condition(
    val type: String,
    val target: String,
    val values: List<Int> = listOf(1),
    val descriptions: Map<Int, String> = emptyMap(),
    val goal: Int? = null // 카운팅 형 미션의 경우 필요
)
