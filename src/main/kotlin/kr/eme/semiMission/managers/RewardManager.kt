package kr.eme.semiMission.managers

import kr.eme.semiMission.objects.model.ItemReward
import kr.eme.semiMission.objects.model.Mission
import kr.eme.semiMoneyGlobal.managers.MoneyManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object RewardManager {
    fun give(mission: Mission, byPlayerName: String) {
        // EP
        val ep = mission.reward.ep
        if (ep > 0 ) {
            MoneyManager.addMoney(ep, "MISSION_${mission.id}_REWARD", byPlayerName)
        }

        // Items
        if (mission.reward.items.isNotEmpty()) {
            val player: Player? = Bukkit.getPlayerExact(byPlayerName)
            if (player != null && player.isOnline) {
                mission.reward.items.forEach { giveItem(player, it) }
                player.sendMessage("§a미션 보상 아이템이 지급되었습니다.")
            }
        }
    }
    private fun giveItem(player: Player, ir: ItemReward) {
        val stack = ItemStack(ir.material, ir.amount)
        val meta = stack.itemMeta

        // 이름/설명 적용: §f 고정
        if (ir.name.isNotBlank()) meta?.setDisplayName("§f${ir.name}")
        if (ir.description.isNotBlank()) meta?.lore = listOf("§f${ir.description}")

        // 커스텀 모델
        if (ir.customModelData != null) meta?.setCustomModelData(ir.customModelData)

        stack.itemMeta = meta

        val left = player.inventory.addItem(stack)
        if (left.isNotEmpty()) {
            // 인벤 가득하면 자연 드롭
            left.values.forEach { player.world.dropItemNaturally(player.location, it) }
        }
    }
}