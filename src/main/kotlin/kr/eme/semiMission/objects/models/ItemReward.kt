package kr.eme.semiMission.objects.models

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.NamespacedKey
import org.bukkit.inventory.EquipmentSlotGroup
import kr.eme.semiMission.main

data class ItemReward(
    val material: Material,
    val amount: Int = 1,
    val name: String = "",
    val description: String = "",
    val customModelData: Int? = null,
    val flags: Set<ItemFlag> = setOf(ItemFlag.HIDE_ATTRIBUTES),
    val metaModifier: (ItemMeta.() -> Unit)? = null
) {
    fun toItemStack(): ItemStack {
        val item = ItemStack(material, amount)
        val meta: ItemMeta = item.itemMeta ?: return item

        if (name.isNotEmpty()) meta.displayName(Component.text(name))
        if (description.isNotEmpty()) meta.lore(listOf(Component.text(description)))
        if (customModelData != null) meta.setCustomModelData(customModelData)
        
        // 1. 기본 속성 제거 (더미 속성 추가로 바닐라 기본 속성 제거)
        // LUCK은 보통 표시되지 않거나 HIDE_ATTRIBUTES로 숨겨짐.
        val dummyModifier = AttributeModifier(
            NamespacedKey(main, "dummy_clear_defaults"),
            0.0,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlotGroup.ANY
        )
        meta.addAttributeModifier(Attribute.LUCK, dummyModifier)
        
        // 기본적으로 모든 속성 숨김 강제 적용
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS)
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON)
        
        if (flags.isNotEmpty()) meta.addItemFlags(*flags.toTypedArray())

        metaModifier?.invoke(meta)

        item.itemMeta = meta
        return item
    }

}
