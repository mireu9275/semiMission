package kr.eme.prcMission.utils

import kr.eme.prcMission.enums.MissionVersion
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

    fun rewardItem(
        material: Material,
        name: String = "",
        lore: List<String>? = null,
        customModelData: Int? = null,
        amount: Int = 1
    ): ItemStack {
        return ItemStack(material, amount).apply {
            itemMeta = itemMeta?.apply {
                if (name.isNotBlank()) setDisplayName(name)
                if (!lore.isNullOrEmpty()) {
                    this.lore = lore
                } else {
                    this.lore = null
                }
                if (customModelData != null) setCustomModelData(customModelData)
            }
        }
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

    fun iconDone(title: String, version: MissionVersion, description: String, reward: String): ItemStack {
        return when (version) {
            MissionVersion.V1 -> withLore(Material.BROWN_DYE, title, description, reward, 3, "§a이미 완료된 미션입니다.")
            MissionVersion.V2 -> withLore(Material.BROWN_DYE, title, description, reward, 24, "§a이미 완료된 미션입니다.")
        }
    }

    fun iconProgress(title: String, version: MissionVersion, description: String, reward: String): ItemStack {
        return when (version) {
            MissionVersion.V1 -> withLore(Material.BROWN_DYE, title, description, reward, 5, "§e진행중인 미션입니다.")
            MissionVersion.V2 -> withLore(Material.BROWN_DYE, title, description, reward, 26, "§e진행중인 미션입니다.")
        }
    }

    fun iconAcceptable(title: String, version: MissionVersion, description: String, reward: String): ItemStack {
        return when (version) {
            MissionVersion.V1 -> withLore(Material.BROWN_DYE, title, description, reward, 7, "§e수락 가능한 미션입니다.")
            MissionVersion.V2 -> withLore(Material.BROWN_DYE, title, description, reward, 28, "§e수락 가능한 미션입니다.")
        }
    }

    fun iconLock(title: String, version: MissionVersion, description: String, reward: String): ItemStack {
        return when (version) {
            MissionVersion.V1 -> withLore(Material.BROWN_DYE, title, description, reward, 7, "§c받지 않은 미션입니다.")
            MissionVersion.V2 -> withLore(Material.BROWN_DYE, title, description, reward, 28, "§c받지 않은 미션입니다.")
        }
    }

    fun iconRewardPending(title: String,  version: MissionVersion, description: String, reward: String): ItemStack {
        return when (version) {
            MissionVersion.V1 -> withLore(Material.BROWN_DYE, title, description, reward, 3, "§a보상을 수령할 수 있습니다.")
            MissionVersion.V2 -> withLore(Material.BROWN_DYE, title, description, reward, 24, "§a보상을 수령할 수 있습니다.")
        }
    }
}
