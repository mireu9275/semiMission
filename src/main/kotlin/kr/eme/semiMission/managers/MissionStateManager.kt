package kr.eme.semiMission.managers

import kr.eme.semiMission.enums.MissionVersion
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object MissionStateManager {
    private val currentIndexMap = mutableMapOf<MissionVersion, Int>()
    private val rewardClaimedMap = mutableMapOf<MissionVersion, MutableSet<Int>>()

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    fun init(dataFolder: File) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs() // 상위 디렉토리 생성
        }

        file = File(dataFolder, "missions.yml")
        if (!file.exists()) {
            file.createNewFile()
        }

        config = YamlConfiguration.loadConfiguration(file)
        load()
    }


    fun getCurrentIndex(version: MissionVersion): Int =
        currentIndexMap[version] ?: -1

    fun setCurrentIndex(version: MissionVersion, index: Int) {
        currentIndexMap[version] = index
    }

    fun acceptFirst(version: MissionVersion): Boolean {
        if (getCurrentIndex(version) == -1) {
            setCurrentIndex(version, 0)
            save()
            return true
        }
        return false
    }

    fun advanceIf(version: MissionVersion, id: Int): Boolean {
        val curIndex = getCurrentIndex(version)
        if (curIndex == -1) return false

        val missions = MissionManager.getMissions(version)
        if (curIndex >= missions.size) return false

        val curMission = missions.getOrNull(curIndex) ?: return false
        if (curMission.id == id) {
            setCurrentIndex(version, curIndex + 1)
            save()
            return true
        }
        return false
    }

    fun isLocked(version: MissionVersion, index: Int): Boolean {
        val curIndex = getCurrentIndex(version)
        return curIndex < index && !(curIndex == -1 && index == 0)
    }

    fun isRewardClaimed(version: MissionVersion, missionId: Int): Boolean {
        return rewardClaimedMap[version]?.contains(missionId) ?: false
    }

    fun markRewardClaimed(version: MissionVersion, missionId: Int) {
        rewardClaimedMap.getOrPut(version) { mutableSetOf() }.add(missionId)
        save()
    }

    fun isVersionCleared(version: MissionVersion): Boolean {
        val missions = MissionManager.getMissions(version)
        if (missions.isEmpty()) return false
        return missions.all { isRewardClaimed(version, it.id) }
    }

    fun canStart(version: MissionVersion): Boolean {
        val allVersions = MissionVersion.entries
        val index = allVersions.indexOf(version)
        if (index <= 0) return true // V1은 항상 시작 가능

        // 앞의 모든 버전이 클리어되어야 true
        return allVersions.take(index).all { isVersionCleared(it) }
    }

    fun save() {
        for (version in MissionVersion.entries) {
            config.set("missions.${version}.currentIndex", getCurrentIndex(version))
            config.set("missions.${version}.rewardsClaimed", rewardClaimedMap[version]?.toList() ?: emptyList<Int>())
        }
        config.save(file)
    }

    private fun load() {
        for (version in MissionVersion.entries) {
            val index = config.getInt("missions.${version}.currentIndex", -1)
            setCurrentIndex(version, index)

            val rewards = config.getIntegerList("missions.${version}.rewardsClaimed").toMutableSet()
            rewardClaimedMap[version] = rewards
        }
    }

    fun reset(version: MissionVersion) {
        setCurrentIndex(version, -1)
        rewardClaimedMap[version] = mutableSetOf()
        save()
    }

    fun resetAll() {
        MissionVersion.entries.forEach { reset(it) }
    }

}
