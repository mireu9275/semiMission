package kr.eme.semiMission.objects.missions.conditionPresets

import kr.eme.semiMission.objects.const.MissionTargets as MTA
import kr.eme.semiMission.objects.const.MissionTypes as MT
import kr.eme.semiMission.objects.models.Condition as C

object ConditionPresetV2 {
    val m1_cond = C(MT.CRAFTING,MTA.CRAFTING,1)
    val m2_cond = C(MT.FARMING,MTA.FARMING_MODULE,1)
    val m3_cond = C(MT.MODULE_UPGRADE,MTA.FURNACE_PROCESS,1)
    val m4_cond = C(MT.CRAFTING,MTA.CRAFTING,1)
    val m5_cond = C(MT.MODULE_UPGRADE,MTA.CRUSHER_PROCESS,1)
    val m6_cond = C(MT.PLAYER_PROGRESS,MTA.FURNACE_PROCESS,1)
    val m7_cond = C(MT.CRAFTING,MTA.PRINTER_MODULE,1)
    val m8_cond = C(MT.PLAYER_PROGRESS,MTA.MINE_MODULE,1)
    val m9_cond = C(MT.DEVICE_INTERACTION,MTA.ACCESS_MODULE,1)
    val m10_cond = C(MT.PLAYER_PROGRESS,MTA.TOWER,1)
    val m11_cond = C(MT.DEVICE_INTERACTION,MTA.HOME_MODULE,1)
    val m12_cond = C(MT.PLAYER_EP,MTA.EP,1)
    val m13_cond = C(MT.MODULE_PLACE,MTA.STORAGE_MODULE,1)
    val m14_cond = C(MT.PLAYER_PROGRESS,MTA.MINE_MODULE,1)
    val m15_cond = C(MT.PLAYER_PROGRESS,MTA.MINE_MODULE,1)
    val m16_cond = C(MT.MODULE_PLACE,MTA.FARMING_MODULE,1)
    val m17_cond = C(MT.PLAYER_PROGRESS,MTA.FARMING_MODULE,1)
    val m18_cond = C(MT.PLAYER_EP,MTA.EP,1)
    val m19_cond = C(MT.CRAFTING,MTA.PRINTER_MODULE,1)
    val m20_cond = C(MT.PLAYER_EP,MTA.EP,1)
}