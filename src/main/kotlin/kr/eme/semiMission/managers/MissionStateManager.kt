package kr.eme.semiMission.managers

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object MissionStateManager {
    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    private var currentIndex: Int = -1   // -1 = 아직 수락한 미션 없음
    private var completedCount: Int = 0 // 누적 완료 미션 수

    fun init(dataFolder: File) {
        if (!dataFolder.exists()) dataFolder.mkdirs()
        file = File(dataFolder, "mission_state.yml")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.writeText(
                """
                    currentIndex: -1
                    completedCount: 0
                """.trimIndent()
            )
        }
        config = YamlConfiguration.loadConfiguration(file)
        currentIndex = config.getInt("currentIndex", -1)
        completedCount = config.getInt("completedCount", 0)

        if (currentIndex < -1 || currentIndex > MissionManager.lastIndex()) currentIndex = -1
        if (completedCount < 0) completedCount = 0
    }

    fun save() {
        config.set("currentIndex", currentIndex)
        config.set("completedCount", completedCount)
        config.save(file)
    }

    @Synchronized fun getCurrentIndex(): Int = currentIndex

    /** 첫 미션을 수락하는 메서드 */
    @Synchronized fun acceptFirst(): Boolean {
        if (currentIndex == -1) {
            currentIndex = 0
            return true
        }
        return false
    }

    /** 현재 미션이 맞다면 완료하고 다음 미션으로 진행 */
    @Synchronized fun advanceIf(id: Int): Boolean {
        if (currentIndex == -1) return false // 아직 수락 안 함
        if (MissionManager.getCurrent(currentIndex)?.id != id) return false

        if (currentIndex < MissionManager.lastIndex()) {
            currentIndex += 1
        } else {
            currentIndex = MissionManager.lastIndex()
        }
        completedCount += 1
        return true
    }

    fun getCompletedCount(): Int = completedCount

    /** 아직 수락 안 했거나, 현재 인덱스보다 큰 경우는 잠금 */
    fun isLocked(index: Int): Boolean {
        return currentIndex == -1 || index > currentIndex
    }

    fun reset() {
        currentIndex = -1
        completedCount = 0
    }

}
