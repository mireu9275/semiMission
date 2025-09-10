package kr.eme.semiMission.objects.guis

import kr.eme.semiMission.managers.GUIManager
import kr.eme.semiMission.utils.ItemStackUtil
import kr.eme.semiMission.utils.SoundUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent

class MissionInitGUI(player: Player) : GUI(player, "§f\\u340F\\u3430", 6) {
    override fun setFirstGUI() {
        // 0.1v icons
        val leftSlots = listOf(
            0, 1, 2, 3,
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30
        )

        // 0.2v icons
        val rightSlots = listOf(
            5, 6, 7, 8,
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35
        )

        val v1Item = ItemStackUtil.build(Material.GLASS_PANE) { meta ->
            meta.setDisplayName("§fMission 0.1v")
            meta.setCustomModelData(1)
        }
        leftSlots.forEach { setItem(it, v1Item) }

        val v2Item = ItemStackUtil.build(Material.GLASS_PANE) { meta ->
            meta.setDisplayName("§fMission 0.2v")
            meta.setCustomModelData(1)
        }
        rightSlots.forEach { setItem(it, v2Item) }
    }

    override fun InventoryClickEvent.clickEvent() {
        isCancelled = true
        val clickedItem = currentItem ?: run {
            SoundUtil.error(player)
            return
        }
        val itemName = clickedItem.itemMeta?.displayName ?: run {
            SoundUtil.error(player)
            return
        }

        when (itemName) {
            "§fMission 0.1v" -> {
                val gui = MissionPageGUI(player, 1)
                gui.setFirstGUI()
                GUIManager.setGUI(player.uniqueId, gui)
                gui.open()
                SoundUtil.click(player)
            }
            "§fMission 0.2v" -> {
                val gui = MissionPageGUI(player, 1)
                gui.setFirstGUI()
                GUIManager.setGUI(player.uniqueId, gui)
                gui.open()
                SoundUtil.click(player)
            }
            else -> SoundUtil.error(player)
        }
    }

    override fun InventoryCloseEvent.closeEvent() {

    }

    override fun InventoryDragEvent.dragEvent() {
        isCancelled = true
    }
}