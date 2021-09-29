package com.bentoboxen.bot

import com.bentoboxen.player.GuildAudioPlayerManager
import com.bentoboxen.player.GuildTrackScheduler
import dev.kord.common.entity.ChannelType
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import kotlinx.coroutines.flow.firstOrNull

object ConnectSoundEffectCommand : Command {
    override fun getNames(): Collection<String> = listOf("cse", "connect")

    override suspend fun command(bot: Bot, event: MessageCreateEvent, vararg params: String) {
        event.message.getGuild().let { guild ->
            guild.channels.firstOrNull {
                it.type == ChannelType.GuildVoice && it.name == (params.firstOrNull() ?: "General")
            }?.let {
                bot.manager.createPlayer(bot, guild.id, it.id, GuildTrackScheduler())
            }
        }
    }
}