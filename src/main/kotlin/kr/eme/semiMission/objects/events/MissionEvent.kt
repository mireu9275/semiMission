package kr.eme.semiMission.objects.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class MissionEvent(
    val player: Player,
    val type: String,   // ex: "DEVICE_INTERACTION", "PLACE", "KILL", "EP_REACH"
    val target: String, // ex: "home_module", "mine_module", "trade_module"
    val value: Int      // 조건 값
) : Event() {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
