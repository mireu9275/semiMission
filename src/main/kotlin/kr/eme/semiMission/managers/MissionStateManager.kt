package kr.eme.semiMission.managers

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object MissionStateManager {
    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    private var currentIndex: Int = 0   // 현재 미션 인덱스
    private var completedCount: Int = 0 // 누적 완료 미션 수

    fun init (dataFolder: File) {
        if (!dataFolder.exists()) dataFolder.mkdirs()
        file = File(dataFolder, "mission_state.yml")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText(
                """
                    currentIndex: 0
                    completedCount: 0
                """.trimIndent()
            )
        }
        config = YamlConfiguration.loadConfiguration(file)
        currentIndex = config.getInt("currentIndex", 0)
        completedCount = config.getInt("completedCount", 0)

        if (currentIndex !in 0..MissionManager.lastIndex()) currentIndex = 0
        if (completedCount < 0) completedCount = 0
    }
    fun save() {
        config.set("currentIndex", currentIndex)
        config.set("completedCount", completedCount)
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
}