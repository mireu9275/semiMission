package kr.eme.semiMission.utils

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ItemStackUtil {
    fun build(material: Material, block: (ItemMeta) -> Unit): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        block(meta)
        item.itemMeta = meta
        return item
    }

    // 네비게이션 버튼
    fun leftButton(name: String): ItemStack = build(Material.BROWN_DYE) { meta ->
        meta.setDisplayName(name)
        meta.setCustomModelData(1)
    }

    fun rightButton(name: String): ItemStack = build(Material.BROWN_DYE) { meta ->
        meta.setDisplayName(name)
        meta.setCustomModelData(2)
    }

    fun withLore(
        material: Material,
        title: String,
        description: String,
        rewardDescription: String,
        customModel: Int,
        status: String
    ): ItemStack = build(material) { meta ->
        meta.setDisplayName(title)
        meta.lore = listOf(
            "§7$description",
            "§f보상: $rewardDescription",
            status
        )
        meta.setCustomModelData(customModel)
    }

    fun iconDone(title: String, description: String, reward: String): ItemStack =
        withLore(Material.BROWN_DYE, title, description, reward, 3, "§a이미 완료된 미션입니다.")

    fun iconProgress(title: String, description: String, reward: String): ItemStack =
        withLore(Material.BROWN_DYE, title, description, reward, 5, "§e진행중인 미션입니다.")

    fun iconAcceptable(title: String, description: String, reward: String): ItemStack =
        withLore(Material.BROWN_DYE, title, description, reward, 6, "§e수락 가능한 미션입니다.")

    fun iconLock(title: String, description: String, reward: String): ItemStack =
        withLore(Material.BROWN_DYE, title, description, reward, 6, "§c받지 않은 미션입니다.")

}
