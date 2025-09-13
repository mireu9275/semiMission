package kr.eme.semiMission.api.events

import kr.eme.semiMission.enums.MissionVersion
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class MissionEvent(
    val player: Player,
    val version: MissionVersion,
    val type: String,
    val target: String,
    val value: Int
) : Event() {

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList = handlerList
}
