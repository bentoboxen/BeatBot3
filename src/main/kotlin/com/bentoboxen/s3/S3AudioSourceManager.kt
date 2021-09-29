package com.bentoboxen.s3

import com.sedmelluq.discord.lavaplayer.container.*
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.ProbingAudioSourceManager
import com.sedmelluq.discord.lavaplayer.track.AudioItem
import com.sedmelluq.discord.lavaplayer.track.AudioReference
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo
import kotlinx.coroutines.runBlocking
import mu.KLogging
import mu.KotlinLogging
import java.io.DataInput
import java.io.DataOutput

class S3AudioSourceManager : ProbingAudioSourceManager(MediaContainerRegistry.DEFAULT_REGISTRY) {

    private val logger = KotlinLogging.logger {}

    override fun getSourceName(): String = "s3"

    override fun loadItem(manager: AudioPlayerManager?, reference: AudioReference?): AudioItem =
        runBlocking {
            logger.debug("s3 load item ${reference?.identifier}")
            S3ResourceManager.get(reference?.identifier)
        }.let {
            logger.debug("handle result ${it?.key} of ${it?.size}")
            handleLoadResult(detectContainerForFile(reference, it))
        }

    override fun isTrackEncodable(track: AudioTrack?): Boolean = false

    override fun encodeTrack(track: AudioTrack?, output: DataOutput?) {
        logger.info("encode?")
    }

    override fun decodeTrack(trackInfo: AudioTrackInfo?, input: DataInput?): AudioTrack? = null

    override fun shutdown() {
        logger.info("shutdown?")
    }

    override fun createTrack(
        trackInfo: AudioTrackInfo?,
        containerTrackFactory: MediaContainerDescriptor?
    ): AudioTrack  = runBlocking {
            S3ResourceManager.get(trackInfo?.identifier)
        }.let {
            logger.debug("create track ${trackInfo?.identifier}")
            S3AudioTrack(
                trackInfo = trackInfo,
                containerDescriptor = containerTrackFactory,
                s3Resource = it
            )
        }

    private fun detectContainerForFile(
        reference: AudioReference?,
        s3Resource: S3Resource?
    ): MediaContainerDetectionResult = s3Resource?.buffer?.let { byteArray ->
            S3SeekableInputStream(byteArray).use {
                MediaContainerDetection(
                    containerRegistry,
                    reference,
                    it,
                    MediaContainerHints.from(s3Resource.contentType, s3Resource.key.split(".")[1])
                ).detectContainer()
            }
        } ?: MediaContainerDetectionResult.unknownFormat()

}