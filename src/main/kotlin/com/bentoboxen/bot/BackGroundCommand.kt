package com.bentoboxen.bot

import com.bentoboxen.player.GuildAudioPlayerManager
import dev.kord.core.event.message.MessageCreateEvent

object BackGroundCommand : Command {
    override fun getNames(): Collection<String> = listOf("b", "bg", "background")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        event.guildId?.let { guildId ->
            bot.manager.load(
                bot,
                guildId,
                params.firstOrNull() ?: "combat"
            )
        }
    }
}