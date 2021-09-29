package com.bentoboxen.bot

import dev.kord.core.event.message.MessageCreateEvent

object PingCommand : Command {
    override fun getNames(): Collection<String> = listOf("ping")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        event.message.channel.createMessage("Pong @${bot.name}")
    }
}