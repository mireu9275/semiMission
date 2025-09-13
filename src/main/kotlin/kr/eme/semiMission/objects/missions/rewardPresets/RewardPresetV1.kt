package kr.eme.semiMission.objects.missions.rewardPresets

import kr.eme.semiMission.objects.models.Reward
import kr.eme.semiMission.objects.items.ItemTemplates as IT

object RewardPresetV1 {
    val m1_reward = Reward(
        500
    )
    val m2_reward = Reward(
        250
    )
    val m3_reward = Reward(
        items = listOf(
            IT.ironIngot.copy(amount = 20)
        )
    )
    val m4_reward = Reward(
        items = listOf(
            IT.ironIngot.copy(amount = 15)
        )
    )
    val m5_reward = Reward(
        items = listOf(
            IT.furnaceModule.copy(amount = 1)
        )
    )
    val m6_reward = Reward(
        300
    )
    val m7_reward = Reward(
        items = listOf(
            IT.ironIngotRecipe.copy(amount = 1)
        )
    )
    val m8_reward = Reward(
        500
    )
    val m9_reward = Reward(
        items = listOf(
            IT.ironIngot.copy(amount = 5),
            IT.aluminumIngot.copy(amount = 3)
        )
    )
    val m10_reward = Reward(
        items = listOf(
            IT.lithiumIngot.copy(amount = 6)
        )
    )
    val m11_reward = Reward(
        550
    )
    val m12_reward = Reward(
        items = listOf(
            IT.ironOre.copy(amount = 10)
        )
    )
    val m13_reward = Reward(
        items = listOf(
            IT.ironOre.copy(amount = 20)
        )
    )
    val m14_reward = Reward(
        750
    )
    val m15_reward = Reward(
        items = listOf(
            IT.mug.copy(amount = 5)
        )
    )
    val m16_reward = Reward(
        800
    )
    val m17_reward = Reward(
        items = listOf(
            IT.copperIngot.copy(amount = 15),
            IT.lithiumIngot.copy(amount = 3)
        )
    )
    val m18_reward = Reward(
        1250
    )
    val m19_reward = Reward(
        2000
    )
    val m20_reward = Reward(
        items = listOf(
            IT.platinumIngot.copy(amount = 15)
        )
    )
}
