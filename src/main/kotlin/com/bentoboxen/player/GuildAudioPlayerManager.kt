package com.bentoboxen.player

import com.bentoboxen.bot.Bot
import com.bentoboxen.s3.S3AudioSourceManager
import com.bentoboxen.s3.S3ResourceManager
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import mu.KotlinLogging

class GuildAudioPlayerManager {
    private val manager: AudioPlayerManager = DefaultAudioPlayerManager()
    private val guildPlayers: MutableMap<Pair<String, Snowflake?>, GuildAudioPlayer> = HashMap()
    private val logger = KotlinLogging.logger {}

    init {
        AudioSourceManagers.registerRemoteSources(manager)
        manager.registerSourceManager(S3AudioSourceManager())
    }

    fun load(bot: Bot, guildId: Snowflake, name: String) {
        logger.debug("load track $name for ${bot.name}-${guildId.value}")
        manager.loadItem(name, BotAudioLoadResultHandler(bot, guildId))
    }

    suspend fun createPlayer(bot: Bot, guildId: Snowflake, channelId: Snowflake): GuildAudioPlayer? =
        GuildAudioPlayer.create(bot, channelId, GuildTrackScheduler())?.also {
            logger.debug("save player ${bot.name}-$guildId of $it")
            guildPlayers[bot.name to guildId] = it
        }

    fun createAudioPlayer(): AudioPlayer = manager.createPlayer()

    fun getPlayer(bot: Bot, guildId: Snowflake): GuildAudioPlayer? = guildPlayers[bot.name to guildId]

}