package kr.eme.semiMission.managers

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object MissionStateManager {
    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    private var currentIndex: Int = -1   // 현재 미션 인덱스 (-1 = 아직 첫 미션 수락 전)
    private var completedCount: Int = 0 // 누적 완료 미션 수
    private val claimedRewards = mutableSetOf<Int>() // 보상 수령 완료된 미션 ID들

    fun init(dataFolder: File) {
        if (!dataFolder.exists()) dataFolder.mkdirs()
        file = File(dataFolder, "mission_state.yml")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText(
                """
                    currentIndex: -1
                    completedCount: 0
                    claimedRewards: []
                """.trimIndent()
            )
        }
        config = YamlConfiguration.loadConfiguration(file)
        currentIndex = config.getInt("currentIndex", -1)
        completedCount = config.getInt("completedCount", 0)
        claimedRewards.clear()
        claimedRewards.addAll(config.getIntegerList("claimedRewards"))

        if (currentIndex !in -1..MissionManager.lastIndex()) currentIndex = -1
        if (completedCount < 0) completedCount = 0
    }

    fun save() {
        config.set("currentIndex", currentIndex)
        config.set("completedCount", completedCount)
        config.set("claimedRewards", claimedRewards.toList())
        config.save(file)
    }

    @Synchronized fun getCurrentIndex(): Int = currentIndex

    @Synchronized fun advanceIf(id: Int): Boolean {
        if (MissionManager.getCurrent(currentIndex)?.id != id) return false
        if (currentIndex < MissionManager.lastIndex()) {
            currentIndex += 1
        }
        completedCount += 1
        return true
    }

    fun getCompletedCount(): Int = completedCount
    fun isLocked(index: Int): Boolean = index > currentIndex

    fun isRewardClaimed(missionId: Int): Boolean = claimedRewards.contains(missionId)

    fun markRewardClaimed(missionId: Int) {
        claimedRewards.add(missionId)
        save()
    }

    fun acceptFirst(): Boolean {
        if (currentIndex == -1) {
            currentIndex = 0
            return true
        }
        return false
    }

    fun reset() {
        currentIndex = -1
        completedCount = 0
        claimedRewards.clear()
        save()
    }
}
