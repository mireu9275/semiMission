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
) : GUI(player, titleFor(version, page), 6) {

    companion object {
        private val PAGE_TITLES_V1 = arrayOf(
            "§f\u340F\u3425", // 1 v1
            "§f\u340F\u3426", // 2 v1
            "§f\u340F\u3427", // 3 v1
            "§f\u340F\u3428", // 4 v1
            "§f\u340F\u3429"  // 5 v1
        )

        private val PAGE_TITLES_V2 = arrayOf(
            "§f\u340F\u3430", // 1 v2
            "§f\u340F\u3431", // 2 v2
            "§f\u340F\u3432", // 3 v2
            "§f\u340F\u3433", // 4 v2
            "§f\u340F\u3434"  // 5 v2
        )

        private const val LAST_PAGE = 5

        private fun titleFor(version: MissionVersion, page: Int): String {
            val titles = when (version) {
                MissionVersion.V1 -> PAGE_TITLES_V1
                MissionVersion.V2 -> PAGE_TITLES_V2
            }
            return titles[(page - 1).coerceIn(0, titles.lastIndex)]
        }

        private val MISSION_SLOTS = intArrayOf(19, 21, 23, 25)
        private const val SLOT_PREV = 36
        private const val SLOT_NEXT = 44
        private const val SLOT_HOME = 49
    }

    override fun setFirstGUI() {
        clear()

        val missions = MissionManager.getMissions(version)
        val curIndex = MissionStateManager.getCurrentIndex(version)
        val start = (page - 1) * MISSION_SLOTS.size

        for (i in MISSION_SLOTS.indices) {
            val global = start + i
            if (global >= missions.size) continue
            val mission = missions[global]

            val icon = when {
                global < curIndex -> {
                    if (MissionStateManager.isRewardClaimed(version, mission.id)) {
                        ItemStackUtil.iconDone(
                            "§f${mission.title}", version,
                            mission.description, mission.rewardDescription
                        )
                    } else {
                        ItemStackUtil.iconRewardPending(
                            "§f${mission.title}", version,
                            mission.description, mission.rewardDescription
                        )
                    }
                }
                global == curIndex -> ItemStackUtil.iconProgress(
                    "§f${mission.title}", version,
                    mission.description, mission.rewardDescription
                )
                else -> {
                    // ✅ 앞 버전이 전부 완료되지 않았다면 잠금
                    if (!MissionStateManager.canStart(version)) {
                        ItemStackUtil.iconLock(
                            "§f${mission.title}", version,
                            mission.description, mission.rewardDescription
                        )
                    }
                    // 첫 미션이고 아직 시작 안 했으면 수락 가능
                    else if (curIndex == -1 && global == 0) {
                        ItemStackUtil.iconAcceptable(
                            "§f${mission.title}", version,
                            mission.description, mission.rewardDescription
                        )
                    } else {
                        ItemStackUtil.iconLock(
                            "§f${mission.title}", version,
                            mission.description, mission.rewardDescription
                        )
                    }
                }
            }
            setItem(MISSION_SLOTS[i], icon)
        }

        if (page > 1) setItem(SLOT_PREV, ItemStackUtil.leftButton("§f이전 페이지"))
        if (page < LAST_PAGE) setItem(SLOT_NEXT, ItemStackUtil.rightButton("§f다음 페이지"))

        val homeItem = ItemStackUtil.build(org.bukkit.Material.GLASS_PANE) { meta ->
            meta.setCustomModelData(1)
            meta.displayName(net.kyori.adventure.text.Component.text("§f메인으로"))
        }
        setItem(SLOT_HOME, homeItem)
    }

    override fun InventoryClickEvent.clickEvent() {
        isCancelled = true

        val clicked = currentItem ?: return
        val name = clicked.itemMeta?.displayName ?: run {
            SoundUtil.error(player); return
        }

        val missions = MissionManager.getMissions(version)
        val curIndex = MissionStateManager.getCurrentIndex(version)

        val slotIndex = MISSION_SLOTS.indexOf(slot)
        if (slotIndex != -1) {
            val global = (page - 1) * MISSION_SLOTS.size + slotIndex
            if (global >= missions.size) return
            val mission = missions[global]

            when {
                // ===== 잠금 상태 =====
                !MissionStateManager.canStart(version) -> {
                    player.sendMessage("§c이전 버전을 모두 완료해야 시작할 수 있습니다!")
                    SoundUtil.error(player)
                }

                // ===== 첫 미션 수락 =====
                curIndex == -1 && global == 0 -> {
                    if (MissionStateManager.acceptFirst(version)) {
                        player.sendMessage("§e[미션 수락] ${mission.title}")
                        SoundUtil.click(player)

                        setItem(
                            MISSION_SLOTS[0],
                            ItemStackUtil.iconProgress(
                                "§f${mission.title}", version,
                                mission.description, mission.rewardDescription
                            )
                        )
                        player.updateInventory()
                    }
                }

                // ===== 이미 지난 미션 (보상 수령) =====
                global < curIndex -> {
                    if (!MissionStateManager.isRewardClaimed(version, mission.id)) {
                        RewardManager.give(mission, player.name)
                        MissionStateManager.markRewardClaimed(version, mission.id)
                        player.sendMessage("§a보상을 수령했습니다!")
                        SoundUtil.complete(player)

                        setItem(
                            slot,
                            ItemStackUtil.iconDone(
                                "§f${mission.title}", version,
                                mission.description, mission.rewardDescription
                            )
                        )
                        player.updateInventory()
                    } else {
                        player.sendMessage("§a이미 완료된 미션입니다.")
                        SoundUtil.error(player)
                    }
                }

                // ===== 현재 진행 중인 미션 =====
                global == curIndex -> {
                    player.sendMessage("§e현재 진행중인 미션: ${mission.title}")
                    player.sendMessage("§7${mission.description}")
                    if (mission.rewardDescription.isNotBlank())
                        player.sendMessage("§b보상: ${mission.rewardDescription}")
                    else
                        player.sendMessage("§b보상: 없음")
                    SoundUtil.click(player)
                }

                // ===== 나머지 (잠금 상태) =====
                else -> {
                    player.sendMessage("§c이 미션은 아직 잠금 상태입니다!")
                    SoundUtil.error(player)
                }
            }
            return
        }

        // ===== 페이지 이동 버튼 처리 =====
        when (name) {
            "§f이전 페이지" -> {
                if (page <= 1) return
                MissionPageGUI(player, version, page - 1).also {
                    it.setFirstGUI(); it.open()
                }
                SoundUtil.click(player)
            }
            "§f다음 페이지" -> {
                if (page >= LAST_PAGE) return
                MissionPageGUI(player, version, page + 1).also {
                    it.setFirstGUI(); it.open()
                }
                SoundUtil.click(player)
            }
            "§f메인으로" -> {
                MissionInitGUI(player).also {
                    it.setFirstGUI(); it.open()
                }
                SoundUtil.click(player)
            }
        }
    }


    override fun InventoryDragEvent.dragEvent() { isCancelled = true }
    override fun InventoryCloseEvent.closeEvent() { /* 유지 */ }
}
