package kr.eme.semiMission.objects.model

data class Reward(
    val ep: Int = 0,
    val items: List<ItemReward> = emptyList()
)
