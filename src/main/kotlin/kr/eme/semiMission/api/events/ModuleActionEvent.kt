package kr.eme.semiMission.api.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ModuleActionEvent (
    val player: Player,
    val action: String,
    val detail: String,
    val amount: Int = 1
    ) : Event(true) {
    override fun getHandlers(): HandlerList = handlerList
    companion object { @JvmStatic val handlerList = HandlerList()}
}