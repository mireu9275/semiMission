package kr.eme.semiMission.managers

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

object GUIManager {
    // 플레이어별 현재 페이지(전역 미션이지만 페이지 이동은 개인 뷰 기준)
    private val pageMap = mutableMapOf<UUID, Int>()

    private const val ROWS = 3
    private const val SIZE = ROWS * 9
    private const val TITLE = "§6§l미션"

    fun openMissionGUI(player: Player) {
        val inv = Bukkit.createInventory(null, SIZE, TITLE)
        val page = pageMap[player.uniqueId] ?: 0

        val missions = MissionManager.missions
        val curIndex = MissionStateManager.getCurrentIndex()

        // 한 페이지에 표시할 슬롯 (가운데 라인 1줄 7칸 등… 여기선 1줄 7칸 예시)
        val slots = listOf(10,11,12,13,14,15,16)
        val start = page * slots.size
        val end = minOf(start + slots.size, missions.size)
        val sub = missions.subList(start, end)

        // 미션 아이콘 구성
        sub.forEachIndexed { i, mission ->
            val globalIndex = start + i
            val item = when {
                globalIndex < curIndex -> ItemStack(Material.EMERALD_BLOCK)     // 완료
                globalIndex == curIndex -> ItemStack(Material.GOLD_BLOCK)       // 진행중
                else -> ItemStack(Material.REDSTONE_BLOCK)                      // 잠금
            }
            val meta = item.itemMeta
            meta?.setDisplayName("§f${mission.title}")
            meta?.lore = listOf("§7${mission.description}")
            item.itemMeta = meta
            inv.setItem(slots[i], item)
        }

        // 네비게이션 버튼
        if (start > 0) {
            val left = ItemStack(Material.ARROW).apply {
                itemMeta = itemMeta.apply { setDisplayName("§e이전 페이지") }
            }
            inv.setItem(18, left)
        }
        if (end < missions.size) {
            val right = ItemStack(Material.ARROW).apply {
                itemMeta = itemMeta.apply { setDisplayName("§e다음 페이지") }
            }
            inv.setItem(26, right)
        }

        player.openInventory(inv)
    }

    fun nextPage(player: Player) {
        val id = player.uniqueId
        val page = (pageMap[id] ?: 0) + 1
        val maxPage = (MissionManager.missions.size - 1) / 7
        pageMap[id] = page.coerceAtMost(maxPage)
        openMissionGUI(player)
    }

    fun previousPage(player: Player) {
        val id = player.uniqueId
        val page = (pageMap[id] ?: 0) - 1
        pageMap[id] = page.coerceAtLeast(0)
        openMissionGUI(player)
    }

    fun resetPage(player: Player) {
        pageMap[player.uniqueId] = 0
    }
}
