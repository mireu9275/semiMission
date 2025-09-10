package kr.eme.semiMission.objects.missions

import kr.eme.semiMission.objects.models.Mission
import kr.eme.semiMission.objects.const.MissionString as MS
import kr.eme.semiMission.objects.missions.rewardPresets.RewardPresetV1 as RP
import kr.eme.semiMission.objects.missions.conditionPresets.ConditionPresetV1 as CP


object MissionsV2 {
    val missions: List<Mission> = listOf (
        Mission(
            1,
            MS.m1_title,
            MS.m1_desc,
            CP.m1_cond,
            RP.m1_reward,
            "§f영양캡슐 5개, 성장캡슐 5개"
        )
    )
}