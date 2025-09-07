package kr.eme.semiMission.managers

import kr.eme.semiMission.objects.model.Condition
import kr.eme.semiMission.objects.model.ItemReward
import kr.eme.semiMission.objects.model.Mission
import kr.eme.semiMission.objects.model.Reward
import org.bukkit.Material

object MissionManager {
    val missions: List<Mission> = listOf(
        //Page 1 (1~4)
        Mission(
            1,
            "기본 아이템 받기",
            "홈 모듈에서 기본 아이템을 지급받으세요.",
            Condition("DEVICE_INTERACTION", "home_module",1),
            Reward(
                ep = 500,
                items = listOf(
                    ItemReward(Material.RED_DYE, 1, "§f철 주괴","", 21)
                )
            ),
            "§fEP 500"
        ),
        Mission(2, "미션 2", "미션 2 설명", Condition("NONE", "", 0), Reward()),
        Mission(3, "미션 3", "미션 3 설명", Condition("NONE", "", 0), Reward()),
        Mission(4, "미션 4", "미션 4 설명", Condition("NONE", "", 0), Reward()),

        // Page 2 (5~8)
        Mission(5, "미션 5", "미션 5 설명", Condition("NONE", "", 0), Reward()),
        Mission(6, "미션 6", "미션 6 설명", Condition("NONE", "", 0), Reward()),
        Mission(7, "미션 7", "미션 7 설명", Condition("NONE", "", 0), Reward()),
        Mission(8, "미션 8", "미션 8 설명", Condition("NONE", "", 0), Reward()),

        // Page 3 (9~12)
        Mission(9, "미션 9", "미션 9 설명", Condition("NONE", "", 0), Reward()),
        Mission(10, "미션 10", "미션 10 설명", Condition("NONE", "", 0), Reward()),
        Mission(11, "미션 11", "미션 11 설명", Condition("NONE", "", 0), Reward()),
        Mission(12, "미션 12", "미션 12 설명", Condition("NONE", "", 0), Reward()),

        // Page 4 (13~16)
        Mission(13, "미션 13", "미션 13 설명", Condition("NONE", "", 0), Reward()),
        Mission(14, "미션 14", "미션 14 설명", Condition("NONE", "", 0), Reward()),
        Mission(15, "미션 15", "미션 15 설명", Condition("NONE", "", 0), Reward()),
        Mission(16, "미션 16", "미션 16 설명", Condition("NONE", "", 0), Reward()),

        // Page 5 (17~20)
        Mission(17, "미션 17", "미션 17 설명", Condition("NONE", "", 0), Reward()),
        Mission(18, "미션 18", "미션 18 설명", Condition("NONE", "", 0), Reward()),
        Mission(19, "미션 19", "미션 19 설명", Condition("NONE", "", 0), Reward()),
        Mission(20, "미션 20", "미션 20 설명", Condition("NONE", "", 0), Reward())
    )
    fun currentIndexOf(id: Int) = missions.indexOfFirst { it.id == id }
    fun getCurrent(index: Int) = missions.getOrNull(index)
    fun firstMissionId(): Int = missions.first().id
    fun lastIndex(): Int = missions.lastIndex
}