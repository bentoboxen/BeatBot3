package com.bentoboxen.player

import com.bentoboxen.bot.Bot
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import dev.kord.common.entity.Snowflake
import mu.KLogging

class BotAudioLoadResultHandler(val bot: Bot, val guildId: Snowflake) : AudioLoadResultHandler {

    companion object : KLogging()

    override fun trackLoaded(track: AudioTrack?) {
        logger.debug("track loaded: play track ${track?.identifier}")
        bot.manager.getPlayer(bot, guildId)?.player?.also {
            logger.debug("get player for ${bot.name}-${guildId.value} is $it")
        }?.startTrack(track, false).also {
            logger.debug("track ${track?.identifier} on  ${bot.name}-${guildId.value} was started? $it")
        }
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        logger.info("playlistloaded")
    }

    override fun noMatches() {
        logger.info("no match?")
        TODO("Not yet implemented")
    }

    override fun loadFailed(exception: FriendlyException?) {
        logger.info("load failed?")
        TODO("Not yet implemented")
    }
}