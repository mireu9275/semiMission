package kr.eme.semiMission.objects.guis

import kr.eme.semiMission.enums.MissionVersion
import kr.eme.semiMission.managers.MissionManager
import kr.eme.semiMission.managers.MissionStateManager
import kr.eme.semiMission.managers.RewardManager
import kr.eme.semiMission.utils.ItemStackUtil
import kr.eme.semiMission.utils.SoundUtil
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent

class MissionPageGUI(
    player: Player,
    private val version: MissionVersion,
    private val page: Int // 1..5
) : GUI(player, titleFor(page), 6) {

    companion object {
        private val PAGE_TITLES = arrayOf(
            "§f\u340F\u3425", // 1
            "§f\u340F\u3426", // 2
            "§f\u340F\u3427", // 3
            "§f\u340F\u3428", // 4
            "§f\u340F\u3429"  // 5
        )
        private const val LAST_PAGE = 5
        private fun titleFor(page: Int) = PAGE_TITLES[(page - 1).coerceIn(0, PAGE_TITLES.lastIndex)]

        private val MISSION_SLOTS = intArrayOf(19, 21, 23, 25)
        private const val SLOT_PREV = 36
        private const val SLOT_NEXT = 44
    }

    override fun setFirstGUI() {
        clear()

        val missions = MissionManager.getMissions(version)
        val curIndex = MissionStateManager.getCurrentIndex()
        val start = (page - 1) * MISSION_SLOTS.size

        for (i in MISSION_SLOTS.indices) {
            val global = start + i
            if (global >= missions.size) continue
            val mission = missions[global]

            val icon = when {
                global < curIndex -> {
                    if (MissionStateManager.isRewardClaimed(mission.id)) {
                        ItemStackUtil.iconDone("§f${mission.title}", mission.description, mission.rewardDescription)
                    } else {
                        ItemStackUtil.iconRewardPending("§f${mission.title}", mission.description, mission.rewardDescription)
                    }
                }
                global == curIndex -> ItemStackUtil.iconProgress("§f${mission.title}", mission.description, mission.rewardDescription)
                else -> {
                    if (curIndex == -1 && global == 0) {
                        ItemStackUtil.iconAcceptable("§f${mission.title}", mission.description, mission.rewardDescription)
                    } else {
                        ItemStackUtil.iconLock("§f${mission.title}", mission.description, mission.rewardDescription)
                    }
                }
            }
            setItem(MISSION_SLOTS[i], icon)
        }

        if (page > 1) setItem(SLOT_PREV, ItemStackUtil.leftButton("§f이전 페이지"))
        if (page < LAST_PAGE) setItem(SLOT_NEXT, ItemStackUtil.rightButton("§f다음 페이지"))
    }

    override fun InventoryClickEvent.clickEvent() {
        isCancelled = true

        val clicked = currentItem ?: return
        val name = clicked.itemMeta?.displayName ?: run {
            SoundUtil.error(player)
            return
        }

        val missions = MissionManager.getMissions(version)
        val curIndex = MissionStateManager.getCurrentIndex()

        val slotIndex = MISSION_SLOTS.indexOf(slot)
        if (slotIndex != -1) {
            val global = (page - 1) * MISSION_SLOTS.size + slotIndex
            if (global >= missions.size) return
            val mission = missions[global]

            when {
                MissionStateManager.isLocked(global) -> {
                    if (global == 0 && curIndex == -1) {
                        if (MissionStateManager.acceptFirst()) {
                            MissionStateManager.save()
                            player.sendMessage("§e[미션 수락] ${mission.title}")
                            SoundUtil.click(player)

                            setItem(
                                MISSION_SLOTS[0],
                                ItemStackUtil.iconProgress(
                                    "§f${mission.title}",
                                    mission.description,
                                    mission.rewardDescription
                                )
                            )
                            player.updateInventory()
                        }
                    } else {
                        player.sendMessage("§c이 미션은 아직 잠금 상태입니다!")
                        SoundUtil.error(player)
                    }
                }

                global < curIndex -> {
                    if (!MissionStateManager.isRewardClaimed(mission.id)) {
                        // ✅ 보상 지급
                        RewardManager.give(mission, player.name)
                        MissionStateManager.markRewardClaimed(mission.id)
                        MissionStateManager.save()
                        player.sendMessage("§a보상을 수령했습니다!")
                        SoundUtil.complete(player)

                        // 🔹 GUI 새로 열지 말고, 해당 슬롯만 교체
                        setItem(
                            slot,
                            ItemStackUtil.iconDone(
                                "§f${mission.title}",
                                mission.description,
                                mission.rewardDescription
                            )
                        )
                        player.updateInventory()
                    } else {
                        player.sendMessage("§a이미 완료된 미션입니다.")
                        SoundUtil.error(player)
                    }
                }

                global == curIndex -> {
                    player.sendMessage("§e현재 진행중인 미션: ${mission.title}")
                    player.sendMessage("§7${mission.description}")
                    if (mission.rewardDescription.isNotBlank())
                        player.sendMessage("§b보상: ${mission.rewardDescription}")
                    else
                        player.sendMessage("§b보상: 없음")
                    SoundUtil.click(player)
                }
            }
            return
        }

        when (name) {
            "§f이전 페이지" -> {
                if (page <= 1) return
                MissionPageGUI(player, version,page - 1).also {
                    it.setFirstGUI(); it.open()
                }
                SoundUtil.click(player)
            }
            "§f다음 페이지" -> {
                if (page >= LAST_PAGE) return
                MissionPageGUI(player, version,page + 1).also {
                    it.setFirstGUI(); it.open()
                }
                SoundUtil.click(player)
            }
        }
    }

    override fun InventoryDragEvent.dragEvent() { isCancelled = true }
    override fun InventoryCloseEvent.closeEvent() { /* 유지 */ }
}
