package kr.eme.prcMission.managers

import kr.eme.prcMission.enums.MissionVersion
import kr.eme.prcMission.objects.models.Mission
import kr.eme.prcMission.objects.models.MissionProgress
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object MissionStateManager {
    private val currentIndexMap = mutableMapOf<MissionVersion, Int>()
    private val rewardClaimedMap = mutableMapOf<MissionVersion, MutableSet<Int>>()

    // 버전별 → 미션ID별 → 진행도
    private val progressMap = mutableMapOf<MissionVersion, MutableMap<Int, MissionProgress>>()

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    fun init(dataFolder: File) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
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
        val curIndex = getCurrentIndex(version)
        return curIndex >= missions.size // 모든 미션을 끝까지 진행 완료했으면 true
    }


    fun canStart(version: MissionVersion): Boolean {
        val allVersions = MissionVersion.entries
        val index = allVersions.indexOf(version)
        if (index <= 0) return true // V1은 항상 시작 가능

        return allVersions.take(index).all { isVersionCleared(it) }
    }

    fun save() {
        for (version in MissionVersion.entries) {
            config.set("missions.${version}.currentIndex", getCurrentIndex(version))
            config.set("missions.${version}.rewardsClaimed", rewardClaimedMap[version]?.toList() ?: emptyList<Int>())

            val progressSection = mutableMapOf<String, Map<String, Any>>()
            progressMap[version]?.forEach { (missionId, prog) ->
                val data = mutableMapOf<String, Any>()
                data["completed"] = prog.completedConditions.toList()
                data["count"] = prog.progressCount   // ✅ 누적 카운트 저장
                progressSection[missionId.toString()] = data
            }
            config.set("missions.${version}.progress", progressSection)
        }
        config.save(file)
    }

    fun load() {
        if (!file.exists()) return

        try {
            config.load(file)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        for (version in MissionVersion.entries) {
            val index = config.getInt("missions.${version}.currentIndex", -1)
            setCurrentIndex(version, index)

            val rewards = config.getIntegerList("missions.${version}.rewardsClaimed").toMutableSet()
            rewardClaimedMap[version] = rewards

            val section = config.getConfigurationSection("missions.${version}.progress")
            val map = mutableMapOf<Int, MissionProgress>()
            if (section != null) {
                for (key in section.getKeys(false)) {
                    val completed = config.getIntegerList("missions.${version}.progress.$key.completed").toMutableSet()
                    val count = config.getInt("missions.${version}.progress.$key.count", 0) // ✅ 누적 카운트 로드
                    val prog = MissionProgress(key, completed, count)
                    map[key.toInt()] = prog
                }
            }
            progressMap[version] = map
        }
    }

    fun reset(version: MissionVersion) {
        setCurrentIndex(version, -1)
        rewardClaimedMap[version] = mutableSetOf()
        progressMap.remove(version)
        save()
    }

    fun resetAll() {
        MissionVersion.entries.forEach { reset(it) }
    }

    fun addProgress(version: MissionVersion, mission: Mission, value: Int) {
        val missionMap = progressMap.getOrPut(version) { mutableMapOf() }
        val missionProgress = missionMap.getOrPut(mission.id) { MissionProgress(mission.id.toString()) }

        val cond = mission.condition

        if (cond.goal != null) {
            // ✅ 누적형 미션
            missionProgress.progressCount += value
            val current = missionProgress.progressCount
            val goal = cond.goal

            if (current >= goal) {
                completeMission(version, mission)
                missionMap.remove(mission.id)
            }
        } else {
            // ✅ 기존 체크리스트형
            missionProgress.completedConditions.add(value)

            val requiredValues = cond.values.toSet()
            if (missionProgress.completedConditions.containsAll(requiredValues)) {
                completeMission(version, mission)
                missionMap.remove(mission.id)
            }
        }
        save() // 진행도 저장
    }

    private fun completeMission(version: MissionVersion, mission: Mission) {
        val curIndex = getCurrentIndex(version)
        val missions = MissionManager.getMissions(version)

        if (curIndex != -1 && curIndex < missions.size) {
            val curMission = missions[curIndex]
            if (curMission.id == mission.id) {
                setCurrentIndex(version, curIndex + 1)
                progressMap[version]?.remove(mission.id)
                save()
            }
        }
    }

    fun getProgress(version: MissionVersion, missionId: Int): MissionProgress {
        val missionMap = progressMap.getOrPut(version) { mutableMapOf() }
        return missionMap.getOrPut(missionId) { MissionProgress(missionId.toString()) }
    }
}
