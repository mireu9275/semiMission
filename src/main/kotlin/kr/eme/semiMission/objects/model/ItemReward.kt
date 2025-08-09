package kr.eme.semiMission.objects.model

import org.bukkit.Material

data class ItemReward(
    val material: Material,
    val amount: Int = 1,
    val name: String = "",
    val description: String = "",
    val customModelData: Int? = null
)
