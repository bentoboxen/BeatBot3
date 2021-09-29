package com.bentoboxen.bot

import com.bentoboxen.player.GuildAudioPlayerManager
import dev.kord.core.event.message.MessageCreateEvent

object DisconnectCommand : Command {
    override fun getNames(): Collection<String> = listOf("d", "disconnect")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        bot.manager.getPlayer(bot, event.message.getGuild().id)?.disconnect()
    }
}