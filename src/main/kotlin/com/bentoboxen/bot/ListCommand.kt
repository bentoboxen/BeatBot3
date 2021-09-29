package com.bentoboxen.bot

import com.bentoboxen.s3.S3ResourceManager
import dev.kord.core.event.message.MessageCreateEvent

object ListCommand : Command {
    override fun getNames(): Collection<String> = listOf("l", "list")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        S3ResourceManager.list().joinToString(
            separator = "\n"
        ).let {
            event.message.channel.createMessage(it)
        }
    }
}