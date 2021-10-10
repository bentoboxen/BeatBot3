package com.bentoboxen.bot

import dev.kord.common.entity.Snowflake
import dev.kord.core.event.message.MessageCreateEvent

object StopCommand : Command {
    override fun getNames(): Collection<String> = listOf("s", "stop")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        event.guildId?.let {
            bot.manager.getPlayer(bot, event.guildId as Snowflake)?.player?.stopTrack()
        }
    }
}