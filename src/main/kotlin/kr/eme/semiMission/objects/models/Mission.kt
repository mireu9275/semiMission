package kr.eme.semiMission.objects.models

data class Mission(
    val id: Int,
    val title: String,
    val description: String,
    val condition: Condition,
    val reward: Reward,
    val rewardDescription: String = ""
)
