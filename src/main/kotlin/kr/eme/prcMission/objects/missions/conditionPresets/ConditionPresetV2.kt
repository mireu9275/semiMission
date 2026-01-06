package kr.eme.prcMission.objects.missions.conditionPresets

import kr.eme.prcMission.objects.const.MissionTargets as MTA
import kr.eme.prcMission.objects.const.MissionTypes as MT
import kr.eme.prcMission.objects.models.Condition as C

object ConditionPresetV2 {
    val m1_cond = C(MT.CRAFTING,MTA.CRAFTING,listOf(1))
    val m2_cond = C(MT.FARMING,MTA.FARMING_MODULE,listOf(1))
    val m3_cond = C(MT.MODULE_UPGRADE,MTA.FURNACE_PROCESS,listOf(1))
    val m4_cond = C(MT.CRAFTING,MTA.CRAFTING,listOf(1))
    val m5_cond = C(MT.MODULE_UPGRADE,MTA.CRUSHER_PROCESS,listOf(1))
    val m6_cond = C(MT.PLAYER_PROGRESS,MTA.FURNACE_PROCESS,listOf(1), goal = 10)
    val m7_cond = C(MT.CRAFTING,MTA.PRINTER_MODULE,listOf(1))
    val m8_cond = C(MT.PLAYER_PROGRESS,MTA.MINE_MODULE,listOf(1), goal = 300)
    val m9_cond = C(MT.MODULE_PLACE,MTA.ACCESS_MODULE,listOf(1))
    val m10_cond = C(MT.PLAYER_PROGRESS,MTA.TOWER,listOf(1))
    val m11_cond = C(MT.DEVICE_INTERACTION,MTA.HOME_MODULE,listOf(1))
    val m12_cond = C(MT.PLAYER_EP,MTA.EP,listOf(1))
    val m13_cond = C(MT.MODULE_PLACE,MTA.STORAGE_MODULE,listOf(1))
    val m14_cond = C(MT.PLAYER_PROGRESS,MTA.MINE_MODULE,listOf(1), goal = 1500)
    val m15_cond = C(MT.PLAYER_PROGRESS,MTA.MINE_MODULE,listOf(1), goal = 500)
    val m16_cond = C(MT.MODULE_PLACE,MTA.FARMING_MODULE,listOf(1))
    val m17_cond = C(MT.PLAYER_PROGRESS,MTA.FARMING_MODULE,listOf(1), goal = 300)
    val m18_cond = C(MT.PLAYER_EP,MTA.EP,listOf(1))
    val m19_cond = C(MT.CRAFTING,MTA.PRINTER_MODULE,listOf(1))
    val m20_cond = C(MT.PLAYER_EP,MTA.EP,listOf(1))
}