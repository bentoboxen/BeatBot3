package com.bentoboxen.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import mu.KotlinLogging

class GuildTrackScheduler() : AudioEventAdapter() {

    private val logger = KotlinLogging.logger {}

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        logger.info("track start")
        super.onTrackStart(player, track)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        logger.info("track end")
        super.onTrackEnd(player, track, endReason)
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        logger.error("track exception", exception)
        logger.error("cause", exception?.cause)
        super.onTrackException(player, track, exception)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        logger.error("track stuck")
        super.onTrackStuck(player, track, thresholdMs)
    }

    override fun onTrackStuck(
        player: AudioPlayer?,
        track: AudioTrack?,
        thresholdMs: Long,
        stackTrace: Array<out StackTraceElement>?
    ) {
        logger.error("track stuck", stackTrace)
        super.onTrackStuck(player, track, thresholdMs, stackTrace)
    }

}