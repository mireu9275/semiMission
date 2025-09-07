package kr.eme.semiMission.utils

import org.bukkit.Sound
import org.bukkit.entity.Player

object SoundUtil {
    fun click(player: Player) {
        player.playSound(player, "minecraft:semicolon.click", 1.3f, 1.0f)
    }

    fun error(player: Player) {
        player.playSound(player, "minecraft:semicolon.error", 1.7f, 1.0f)
    }

    fun complete(player: Player) {
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f)
    }
}