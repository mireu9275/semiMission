package kr.eme.semiMission.objects.models

data class Condition(
    val type: String,
    val target: String,
    val need: Int = 1
)
