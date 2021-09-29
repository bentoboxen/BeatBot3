package com.bentoboxen.player

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason

class GuildBackGroundTrackScheduler : AudioEventAdapter() {
    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        if(endReason == AudioTrackEndReason.FINISHED) {
            player?.playTrack(track?.makeClone())
        }
    }
}