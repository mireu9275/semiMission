package kr.eme.semiMission.managers

import kr.eme.semiMission.objects.models.ItemReward
import kr.eme.semiMission.objects.models.Mission
import kr.eme.semiMission.utils.ItemStackUtil
import kr.eme.semiMission.utils.SoundUtil
import kr.eme.semiMoneyGlobal.managers.MoneyManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object RewardManager {
    fun give(mission: Mission, byPlayerName: String) {
        val ep = mission.reward.ep
        if (ep > 0) {
            MoneyManager.addMoney(ep, "MISSION_${mission.id}_REWARD", byPlayerName)
        }

        if (mission.reward.items.isNotEmpty()) {
            val player: Player? = Bukkit.getPlayerExact(byPlayerName)
            if (player != null && player.isOnline) {
                mission.reward.items.forEach { giveItem(player, it) }
                player.sendMessage("§a미션 보상 아이템이 지급되었습니다.")
            }
        }
        val player: Player? = Bukkit.getPlayerExact(byPlayerName)
        if (player != null && player.isOnline) {
            SoundUtil.complete(player)
        }
    }
    private fun giveItem(player: Player, ir: ItemReward) {
        val stack = ItemStackUtil.rewardItem(
            material = ir.material,
            name = ir.name,
            lore = if (ir.description.isNotBlank()) listOf(ir.description) else null,
            customModelData = ir.customModelData,
            amount = ir.amount
        )

        val left = player.inventory.addItem(stack)
        if (left.isNotEmpty()) {
            left.values.forEach { player.world.dropItemNaturally(player.location, it) }
        }
    }
}