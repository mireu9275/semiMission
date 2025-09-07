package kr.eme.semiMission.objects.model

import org.bukkit.Material

object RewardPreset {
    val m1_reward = Reward(
        500
    )
    val m2_reward = Reward(
        250
    )
    val m3_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 20, "§f철 주괴","", 21)
        )
    )
    val m4_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 15, "§f철 주괴","", 21)
        )
    )
    val m5_reward = Reward(
        items = listOf(
            ItemReward(Material.IRON_HORSE_ARMOR, 1, "§f용광로 모듈","", 6)
            //용광로 모듈 1개
        )
    )
    val m6_reward = Reward(
        300
    )
    val m7_reward = Reward(
        items = listOf(
            ItemReward(Material.SADDLE, 1, "§f철 주괴 레시피","", 25)
            //철 주괴 레시피 1개
        )
    )
    val m8_reward = Reward(
        500
    )
    val m9_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 5, "§f철 주괴","", 21),
            ItemReward(Material.RED_DYE, 3, "§f알루미늄 주괴","", 20)
            //알루미늄 주괴 3
        )
    )
    val m10_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 6, "§f리튬 주괴","", 23)
            // 리튬 주괴 6
        )
    )
    val m11_reward = Reward(
        550
    )
    val m12_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 10, "§f철","", 3)
            //철10개
        )
    )
    val m13_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 20, "§f철","", 3)
            //철20개
        )
    )
    val m14_reward = Reward(
        750
    )
    val m15_reward = Reward(
        items = listOf(
            ItemReward(Material.BOWL, 5, "§f머그잔","§fJava", 1)
            //머그잔5개
        )
    )
    val m16_reward = Reward(
        800
    )
    val m17_reward = Reward(
        items = listOf(
            ItemReward(Material.RED_DYE, 15, "§f구리 주괴","", 22),
            ItemReward(Material.RED_DYE, 3, "§f리튬 주괴","", 23)
            //구리주괴 15개 리튬 주괴 3개
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
            ItemReward(Material.RED_DYE, 15, "§f백금 주괴","", 25)
            //백금 주괴 15
        )
    )
}