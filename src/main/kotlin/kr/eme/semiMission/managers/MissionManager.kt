package kr.eme.semiMission.managers
import kr.eme.semiMission.enums.MissionVersion
import kr.eme.semiMission.objects.missions.MissionsV1
import kr.eme.semiMission.objects.missions.MissionsV2
import kr.eme.semiMission.objects.models.Mission
import kotlin.collections.first
import kotlin.collections.getOrNull
import kotlin.collections.indexOfFirst
import kotlin.collections.lastIndex

object MissionManager {
    private val missionMap: Map<MissionVersion, List<Mission>> = mapOf(
        MissionVersion.V1 to MissionsV1.missions,
        MissionVersion.V2 to MissionsV2.missions
    )

    fun getMissions(version: MissionVersion): List<Mission> {
        return missionMap[version] ?: emptyList()
    }
    fun currentIndexOf(version: MissionVersion, id: Int) = getMissions(version).indexOfFirst { it.id == id }
    fun getCurrent(version: MissionVersion, index: Int) = getMissions(version).getOrNull(index)
    fun lastIndex(version: MissionVersion): Int = getMissions(version).lastIndex
}