package kr.eme.prcMission

import kr.eme.prcMission.commands.MissionCommand
import kr.eme.prcMission.listeners.GUIListener
import kr.eme.prcMission.listeners.MissionProgressListener
import kr.eme.prcMission.managers.MissionStateManager
import org.bukkit.plugin.java.JavaPlugin

class PRCMission : JavaPlugin() {
    override fun onEnable() {
        main = this
        MissionStateManager.init(dataFolder)
        registerCommands()
        registerEvents()
        logger.info("SemiMission 플러그인이 활성화되었습니다.")
    }
    override fun onDisable() {
        logger.info("SemiMission 플러그인이 비활성화되었습니다.")
    }
    private fun registerCommands() {
        getCommand("mission")?.let { cmd ->
            cmd.setExecutor(MissionCommand)
            cmd.tabCompleter = MissionCommand
        }
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(GUIListener, this)
        server.pluginManager.registerEvents(MissionProgressListener, this)
    }
}