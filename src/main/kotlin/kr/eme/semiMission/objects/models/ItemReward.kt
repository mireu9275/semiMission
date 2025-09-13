package kr.eme.semiMission.objects.models

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

data class ItemReward(
    val material: Material,
    val amount: Int = 1,
    val name: String = "",
    val description: String = "",
    val customModelData: Int? = null
) {
    fun toItemStack(): ItemStack {
        val item = ItemStack(material, amount)
        val meta: ItemMeta = item.itemMeta ?: return item

        if (name.isNotEmpty()) meta.displayName(Component.text(name))
        if (description.isNotEmpty()) meta.lore(listOf(Component.text(description)))
        if (customModelData != null) meta.setCustomModelData(customModelData)

        item.itemMeta = meta
        return item
    }

}
