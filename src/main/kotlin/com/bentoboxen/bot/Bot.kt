package com.bentoboxen.bot

import com.bentoboxen.player.GuildAudioPlayerManager
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on

class Bot(val name: String, val client: Kord, private val commands: Map<String, Command>) {

    private val commandKeys = listOf("bb", "beatbot")
    val manager = GuildAudioPlayerManager()

    companion object {
        suspend fun create(
            name: String,
            token: String,
            vararg commands: Command
        ): Bot = Bot(
            name,
            Kord(token),
            commands
                .toList()
                .map { command ->
                    command.getNames().map {
                        it to command
                    }
                }.flatten()
                .associate { it }
        )
    }

    init {
        client.on<MessageCreateEvent> { handle() }
    }

    suspend fun start() {
        client.login()
    }

    private suspend fun MessageCreateEvent.handle() {
        message.content.split(" ").takeIf { parts ->
            commandKeys.any { parts.first().equals(it, ignoreCase = true) }
        }?.let { params ->
            commands[params[1]]?.let { command ->
                command.command(this@Bot, this, *(params.takeLast(params.size - 2).toTypedArray()))
            }
        }
    }
}