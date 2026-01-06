package kr.eme.prcMission.objects.missions.rewardPresets

import kr.eme.prcMission.objects.models.Reward
import kr.eme.prcMission.objects.items.ItemTemplates as IT

object RewardPresetV2 {
    val m1_reward = Reward(
        items = listOf(
            IT.growthCapsule.copy(amount = 5),
            IT.nutrientCapsule.copy(amount = 5)
        )
    )
    val m2_reward = Reward(
        items = listOf(
            IT.ironIngot.copy(amount = 3),
            IT.copperIngot.copy(amount = 3)
        )
    )
    val m3_reward = Reward(
        items = listOf(
            IT.ALCURecipe.copy(amount = 1)
        )
    )
    val m4_reward = Reward(
        items = listOf(
            IT.ALCUIngot.copy(amount = 3)
        )
    )
    val m5_reward = Reward(
        items = listOf(
            IT.CUAUIngot.copy(amount = 10)
        )
    )
    val m6_reward = Reward(
        1250
    )
    val m7_reward = Reward(
        items = listOf(
            IT.knife.copy(amount = 1)
        )
    )
    val m8_reward = Reward(
        items = listOf(
            IT.ironIngot.copy(amount = 30)
        )
    )
    val m9_reward = Reward(
        items = listOf(
            IT.coffee.copy(amount = 20),
            IT.transportationItem.copy(amount = 1)
        )
    )
    val m10_reward = Reward(
        3125
    )
    val m11_reward = Reward(
        2200
    )
    val m12_reward = Reward(
        items = listOf(
            IT.ironIngot.copy(amount = 35)
        )
    )
    val m13_reward = Reward(
        items = listOf(
            IT.drill.copy(amount = 1)
        )
    )
    val m14_reward = Reward(
        items = listOf(
            IT.longSword.copy(amount = 1),
            IT.knife.copy(amount = 1)
        )
    )
    val m15_reward = Reward(
        items = listOf(
            IT.lithiumIngot.copy(amount = 4),
            IT.titaniumIngot.copy(amount = 1)
        )
    )
    val m16_reward = Reward(
        items = listOf(
            IT.growthCapsule.copy(amount = 15),
            IT.nutrientCapsule.copy(amount = 15),
            IT.weedkillerCapsule.copy(amount = 15)
        )
    )
    val m17_reward = Reward(
        4000
    )
    val m18_reward = Reward(
        items = listOf(
            IT.nickelIngot.copy(amount = 1),
            IT.titaniumIngot.copy(amount = 2),
            IT.platinumIngot.copy(amount = 1)
        )
    )
    val m19_reward = Reward(
        6000
    )
    val m20_reward = Reward(
        items = listOf(
            // 엔딩 접근용 아이템
        )
    )
}