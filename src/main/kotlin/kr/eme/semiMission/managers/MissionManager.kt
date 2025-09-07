package kr.eme.semiMission.managers
import kr.eme.semiMission.objects.const.MissionString as MS
import kr.eme.semiMission.objects.model.ConditionPreset as CP
import kr.eme.semiMission.objects.model.RewardPreset as RP
import kr.eme.semiMission.objects.model.Mission

object MissionManager {
    val missions: List<Mission> = listOf(
        //Page 1 (1~4)
        Mission(
            1,
            MS.m1_title,
            MS.m1_desc,
            CP.m1_cond,
            RP.m1_reward,
            "§fEP 500"
        ),

        Mission(
            2,
            MS.m2_title,
            MS.m2_desc,
            CP.m2_cond,
            RP.m2_reward,
            "§fEP 250"
        ),

        Mission(
            3,
            MS.m3_title,
            MS.m3_desc,
            CP.m3_cond,
            RP.m3_reward,
            "§f철 주괴 20개"
        ),

        Mission(
            4,
            MS.m4_title,
            MS.m4_desc,
            CP.m4_cond,
            RP.m4_reward,
            "§f철 주괴 15개"
        ),

        // Page 2 (5~8)

        Mission(
            5,
            MS.m5_title,
            MS.m5_desc,
            CP.m5_cond,
            RP.m5_reward,
            "§f용광로 모듈 1개"
        ),

        Mission(
            6,
            MS.m6_title,
            MS.m6_desc,
            CP.m6_cond,
            RP.m6_reward,
            "§fEP 300"
        ),

        Mission(
            7,
            MS.m7_title,
            MS.m7_desc,
            CP.m7_cond,
            RP.m7_reward,
            "§f철 주괴 레시피 1개"
        ),
        Mission(
            8,
            MS.m8_title,
            MS.m8_desc,
            CP.m8_cond,
            RP.m8_reward,
            "§fEP 500"
        ),

        // Page 3 (9~12)
        Mission(
            9,
            MS.m9_title,
            MS.m9_desc,
            CP.m9_cond,
            RP.m9_reward,
            "§f알루미늄 주괴 3개, 철 주괴 5개"
        ),
        Mission(
            10,
            MS.m10_title,
            MS.m10_desc,
            CP.m10_cond,
            RP.m10_reward,
            "§f리튬 주괴 6개"
        ),
        Mission(
            11,
            MS.m11_title,
            MS.m11_desc,
            CP.m11_cond,
            RP.m11_reward,
            "§fEP 550"
        ),
        Mission(
            12,
            MS.m12_title,
            MS.m12_desc,
            CP.m12_cond,
            RP.m12_reward,
            "§f철 10개"
        ),

        // Page 4 (13~16)
        Mission(
            13,
            MS.m13_title,
            MS.m13_desc,
            CP.m13_cond,
            RP.m13_reward,
            "§f철 20개"
        ),
        Mission(
            14,
            MS.m14_title,
            MS.m14_desc,
            CP.m14_cond,
            RP.m14_reward,
            "§fEP 750"
        ),
        Mission(
            15,
            MS.m15_title,
            MS.m15_desc,
            CP.m15_cond,
            RP.m15_reward,
            "§f커피잔 5개"
        ),
        Mission(
            16,
            MS.m16_title,
            MS.m16_desc,
            CP.m16_cond,
            RP.m16_reward,
            "§fEP 800"
        ),

        // Page 5 (17~20)
        Mission(
            17,
            MS.m17_title,
            MS.m17_desc,
            CP.m17_cond,
            RP.m17_reward,
            "§f구리 주괴 15, 리튬 주고 ㅣ3개"
        ),
        Mission(
            18,
            MS.m18_title,
            MS.m18_desc,
            CP.m18_cond,
            RP.m18_reward,
            "§fEP 1250"
        ),
        Mission(
            19,
            MS.m19_title,
            MS.m19_desc,
            CP.m19_cond,
            RP.m19_reward,
            "§fEP 2000"
        ),
        Mission(
            20,
            MS.m20_title,
            MS.m20_desc,
            CP.m20_cond,
            RP.m20_reward,
            "§f백금 주괴 15개"
        ),
    )
    fun currentIndexOf(id: Int) = missions.indexOfFirst { it.id == id }
    fun getCurrent(index: Int) = missions.getOrNull(index)
    fun firstMissionId(): Int = missions.first().id
    fun lastIndex(): Int = missions.lastIndex
}