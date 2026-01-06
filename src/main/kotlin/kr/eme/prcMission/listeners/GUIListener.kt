package kr.eme.prcMission.listeners

import kr.eme.prcMission.managers.GUIManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent

object GUIListener : Listener {
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        val gui = GUIManager.getGUI(e.whoClicked.uniqueId) ?: return
        if (e.view.title != gui.title) return
        gui.onClick(e)
    }

    @EventHandler
    fun onDrag(e: InventoryDragEvent) {
        val gui = GUIManager.getGUI(e.whoClicked.uniqueId) ?: return
        if (e.view.title != gui.title) return
        gui.onDrag(e)
    }

    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        val gui = GUIManager.getGUI(e.player.uniqueId) ?: return
        if (e.view.title != gui.title) return
        gui.onClose(e)
    }
}
