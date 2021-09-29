package com.bentoboxen.bot

import dev.kord.core.event.message.MessageCreateEvent

interface Command {
    fun getNames(): Collection<String>
    suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String)
}