package kr.eme.semiMission.objects.model

import kr.eme.semiMission.objects.const.MissionTargets
import kr.eme.semiMission.objects.const.MissionTypes

object ConditionPreset {
    val m1_cond = Condition(MissionTypes.DEVICE_INTERACTION, MissionTargets.HOME_MODULE,1)
    val m2_cond = Condition(MissionTypes.MODULE_PLACE, MissionTargets.MINE_MODULE,1)
    val m3_cond = Condition(MissionTypes.PLAYER_PROGRESS,MissionTargets.MINE_MODULE,1)
    val m4_cond = Condition(MissionTypes.TRADE,MissionTargets.TRADE_MODULE,1)
    val m5_cond = Condition(MissionTypes.MODULE_PLACE,MissionTargets.STORAGE_MODULE,1)
    val m6_cond = Condition(MissionTypes.MODULE_PLACE,MissionTargets.STORAGE_MODULE,1)
    val m7_cond = Condition(MissionTypes.DEVICE_INTERACTION,MissionTargets.CRUSHER_PROCESS,1)
    val m8_cond = Condition(MissionTypes.DEVICE_INTERACTION,MissionTargets.FURNACE_PROCESS,1)
    val m9_cond = Condition(MissionTypes.MODULE_PLACE,MissionTargets.PRINTER_MODULE,1)
    val m10_cond = Condition(MissionTypes.CRAFTING,MissionTargets.CRAFTING,1)
    val m11_cond = Condition(MissionTypes.DEVICE_INTERACTION,MissionTargets.WRENCH_PICKUP,1)
    val m12_cond = Condition(MissionTypes.FARMING,MissionTargets.FARMING_MODULE,1)
    val m13_cond = Condition(MissionTypes.MODULE_PLACE,MissionTargets.CROSS_EXTENSION,1)
    val m14_cond = Condition(MissionTypes.TRADE,MissionTargets.TRADE_MODULE,1)
    val m15_cond = Condition(MissionTypes.TRADE,MissionTargets.COFFEE_MODULE,1)
    val m16_cond = Condition(MissionTypes.DEVICE_INTERACTION,MissionTargets.COFFEE_MODULE,1)
    val m17_cond = Condition(MissionTypes.PLAYER_PROGRESS,MissionTargets.MINE_MODULE,1)
    val m18_cond = Condition(MissionTypes.UPGRADE,MissionTargets.UPGRADE,1)
    val m19_cond = Condition(MissionTypes.PLAYER_EP,MissionTargets.EP_300K,1)
    val m20_cond = Condition(MissionTypes.PLAYER_EP,MissionTargets.EP_500K,1)
}