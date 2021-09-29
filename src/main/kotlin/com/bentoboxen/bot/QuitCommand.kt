package com.bentoboxen.bot

import dev.kord.core.event.message.MessageCreateEvent

object QuitCommand : Command {
    override fun getNames(): Collection<String> = listOf("q", "quit")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        bot.client.logout()
    }
}