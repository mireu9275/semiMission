package kr.eme.semiMission.objects.missions.conditionPresets

import kr.eme.semiMission.objects.const.MissionTargets as MTA
import kr.eme.semiMission.objects.const.MissionTypes as MT
import kr.eme.semiMission.objects.models.Condition as C

object ConditionPresetV1 {
    val m1_cond = C(MT.DEVICE_INTERACTION, MTA.HOME_MODULE, listOf(1))
    val m2_cond = C(MT.MODULE_PLACE, MTA.MINE_MODULE, listOf(1), goal = 2)
    val m3_cond = C(MT.PLAYER_PROGRESS, MTA.MINE_MODULE, listOf(1))
    val m4_cond = C(MT.TRADE, MTA.TRADE_MODULE, listOf(1))
    val m5_cond = C(MT.MODULE_PLACE, MTA.STORAGE_MODULE, listOf(1))
    val m6_cond = C(MT.MODULE_PLACE, MTA.STORAGE_MODULE, listOf(1,2), mapOf(1 to "분쇄기 모듈 설치", 2 to "용광로 모듈 설치"))
    val m7_cond = C(MT.DEVICE_INTERACTION, MTA.CRUSHER_PROCESS, listOf(1))
    val m8_cond = C(MT.DEVICE_INTERACTION, MTA.FURNACE_PROCESS, listOf(1))
    val m9_cond = C(MT.MODULE_PLACE, MTA.PRINTER_MODULE, listOf(1))
    val m10_cond = C(MT.CRAFTING, MTA.CRAFTING, listOf(1,2),mapOf(1 to "단단한 괭이 제작", 2 to "소형 물뿌리개 제작"))
    val m11_cond = C(MT.DEVICE_INTERACTION, MTA.WRENCH_PICKUP, listOf(1))
    val m12_cond = C(MT.FARMING, MTA.FARMING_MODULE, listOf(1))
    val m13_cond = C(MT.MODULE_PLACE, MTA.CROSS_EXTENSION, listOf(1))
    val m14_cond = C(MT.TRADE, MTA.TRADE_MODULE, listOf(1))
    val m15_cond = C(MT.TRADE, MTA.COFFEE_MODULE, listOf(1,2),mapOf(1 to "커피 모듈 구매", 2 to "커피 씨앗 구매"))
    val m16_cond = C(MT.DEVICE_INTERACTION, MTA.COFFEE_MODULE, listOf(1))
    val m17_cond = C(MT.PLAYER_PROGRESS, MTA.MINE_MODULE, listOf(1), goal = 40)
    val m18_cond = C(MT.UPGRADE, MTA.UPGRADE, listOf(1,2,3),mapOf(1 to "곡괭이 업그레이드", 2 to "괭이 업그레이드", 3 to "물뿌리개 업그레이드"))
    val m19_cond = C(MT.PLAYER_EP, MTA.EP, listOf(1))
    val m20_cond = C(MT.PLAYER_EP, MTA.EP, listOf(1))
}