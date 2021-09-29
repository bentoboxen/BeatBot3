package com.bentoboxen.player

import com.bentoboxen.bot.Bot
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.channel.VoiceChannel
import dev.kord.voice.AudioFrame
import dev.kord.voice.VoiceConnection
import mu.KLogging

data class GuildAudioPlayer(
    private val voiceConnection: VoiceConnection,
    val player: AudioPlayer
) {

    suspend fun disconnect() {
        player.destroy()
        voiceConnection.leave()
    }

    companion object : KLogging() {
        suspend fun create(
            bot: Bot,
            channelId: Snowflake,
            scheduler: AudioEventAdapter
        ): GuildAudioPlayer? {
            bot.manager.createAudioPlayer().let { player ->
                bot.client.getChannelOf<VoiceChannel>(channelId).let { channel ->
                    logger.debug("connect channel $channel")
                    player.addListener(scheduler)
                    channel?.connect {
                        audioProvider { AudioFrame.fromData(player.provide()?.data) }
                    }?.let { connection ->
                        return GuildAudioPlayer(connection, player)
                    }
                }
            }
            return null
        }
    }
}