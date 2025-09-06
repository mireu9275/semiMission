package kr.eme.semiMission.utils

import org.bukkit.entity.Player

object SoundUtil {
    fun click(player: Player) {
        player.playSound(player, "minecraft:semicolon.click", 1.3f, 1.0f)
    }

    fun error(player: Player) {
        player.playSound(player, "minecraft:semicolon.error", 1.7f, 1.0f)
    }
}