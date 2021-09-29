package com.bentoboxen.s3

import com.sedmelluq.discord.lavaplayer.container.MediaContainerDescriptor
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo
import com.sedmelluq.discord.lavaplayer.track.DelegatedAudioTrack
import com.sedmelluq.discord.lavaplayer.track.InternalAudioTrack
import com.sedmelluq.discord.lavaplayer.track.playback.LocalAudioTrackExecutor
import mu.KLogging

class S3AudioTrack(
    private val trackInfo: AudioTrackInfo?,
    private val containerDescriptor: MediaContainerDescriptor?,
    private val s3Resource: S3Resource?
) : DelegatedAudioTrack(trackInfo) {

    companion object : KLogging()

    override fun process(executor: LocalAudioTrackExecutor?) {
        logger.debug("process ${trackInfo.identifier}/${s3Resource?.key}")
        s3Resource?.buffer?.let { byteArray ->
            containerDescriptor?.let { descriptor ->
                S3SeekableInputStream(byteArray).use {
                    processDelegate(descriptor.createTrack(trackInfo, it) as InternalAudioTrack, executor)
                }
            }
        }
    }

    override fun isSeekable(): Boolean {
        return true
    }

    override fun makeShallowClone(): AudioTrack {
        return S3AudioTrack(
            trackInfo = this.trackInfo,
            containerDescriptor = this.containerDescriptor,
            s3Resource = this.s3Resource
        )
    }
}