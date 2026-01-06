package kr.eme.prcMission.objects.guis

import kr.eme.prcMission.managers.GUIManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class GUI(
    val player: Player,
    // 예) "§f\\u340F\\u3424"  ← 유니코드 이스케이프를 그대로 넘김
    val titleRaw: String,
    rowsOrType: Any // Int (rows) 또는 InventoryType
) {
    val title: String = decodeUnicode(titleRaw)
    private val size: Int
    private val type: InventoryType?
    private val inventory: Inventory

    init {
        if (rowsOrType is Int) {
            size = rowsOrType.coerceIn(1, 6) * 9
            type = null
            inventory = Bukkit.createInventory(null, size, title)
        } else {
            type = rowsOrType as InventoryType
            size = type.defaultSize
            inventory = Bukkit.createInventory(null, type, title)
        }
    }

    fun getItem(slot: Int): ItemStack? = inventory.getItem(slot)
    fun setItem(slot: Int, itemStack: ItemStack?) = inventory.setItem(slot, itemStack)

    fun clear() {
        for (slot in 0 until inventory.size) inventory.setItem(slot, null)
    }

    fun open() {
        GUIManager.setGUI(player.uniqueId, this)
        player.openInventory(inventory)
    }

    // 라우팅용
    fun onClick(e: InventoryClickEvent) = e.run { clickEvent() }
    fun onDrag(e: InventoryDragEvent) = e.run { dragEvent() }
    fun onClose(e: InventoryCloseEvent) = e.run {
        closeEvent()
        // 필요 시 닫을 때 제거 (원하면 유지)
        GUIManager.removeGUI(player.uniqueId)
    }

    abstract fun setFirstGUI()
    abstract fun InventoryClickEvent.clickEvent()
    abstract fun InventoryDragEvent.dragEvent()
    abstract fun InventoryCloseEvent.closeEvent()

    companion object {
        // "§f\\u340F\\u3424" → "§f\u340F\u3424"
        fun decodeUnicode(input: String): String =
            input.replace("""\\u([0-9a-fA-F]{4})""".toRegex()) {
                it.groupValues[1].toInt(16).toChar().toString()
            }
    }
}
