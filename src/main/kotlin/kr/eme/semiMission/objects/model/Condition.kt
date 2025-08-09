package kr.eme.semiMission.objects.model

data class Condition(
    val type: String,
    val target: String,
    val need: Int = 1
)
