package com.bentoboxen.s3

import com.sedmelluq.discord.lavaplayer.tools.io.SeekableInputStream
import com.sedmelluq.discord.lavaplayer.track.info.AudioTrackInfoProvider

class S3SeekableInputStream(
    private val byteArray: ByteArray,
    private var position: Long = 0
) : SeekableInputStream(byteArray.size.toLong(), byteArray.size.toLong()) {

    override fun read(): Int = position
        .takeIf { it < byteArray.size }
        ?.let {
            (byteArray[it.toInt()].toInt() and 0xFF).apply { position += 1 }
        } ?: -1

    override fun skip(n: Long): Long {
        if(position + n > byteArray.size) {
            return (byteArray.size - position).also {
                position = byteArray.size.toLong()
            }
        } else {
            return n.also {
                position += n
            }
        }
    }

    override fun available(): Int {
        return (byteArray.size - position).toInt()
    }

    override fun getPosition(): Long = position

    override fun seekHard(position: Long) {
        this.position = position
    }

    override fun canSeekHard(): Boolean = true

    override fun getTrackInfoProviders(): MutableList<AudioTrackInfoProvider> = mutableListOf()
}