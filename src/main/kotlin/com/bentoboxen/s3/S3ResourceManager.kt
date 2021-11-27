package com.bentoboxen.s3

import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.toByteArray
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentNavigableMap
import java.util.concurrent.ConcurrentSkipListMap
import kotlin.math.abs

object S3ResourceManager {

    private val nameRegex = Regex("^.*/([\\w\\d-_]+)\\.[\\w\\d]+$")

    private val resources: ConcurrentSkipListMap<String, S3Resource> = ConcurrentSkipListMap { a, b ->
        a.lowercase().compareTo(b.lowercase())
    }

    suspend fun init() {
        S3Client {
            region = "us-west-2"
        }.listObjects {
            bucket = "bentoboxen-beatbot"
        }.contents?.mapNotNull {
            it.key
        }?.forEach {
            resources[it.stripKey()] = S3Resource(key = it)
        }
    }

    fun list() = resources.keys

    suspend fun get(name: String?): S3Resource? =
        name?.let { n ->
            resources.nearestValue(n)?.let { resource ->
                resource.takeIf { it.size != null }
                    ?: S3Client {
                        region = "us-west-2"
                    }.getObject(GetObjectRequest {
                        key = resource.key
                        bucket = "bentoboxen-beatbot"
                    }) { response ->
                        S3Resource(
                            key = resource.key,
                            buffer = response.body?.toByteArray(),
                            contentType = response.contentType,
                            size = response.contentLength,
                            timestamp = Date()
                        ).also {
                            resources[it.key.stripKey()] = it
                        }
                    }
            }
        }

    private fun String?.stripKey(): String = this
        ?.let { nameRegex.find(this) }
        ?.let {
            it.groups[1]?.value
        } ?: this ?: ""


    private fun <V> ConcurrentNavigableMap<String, V>.nearestValue(key: String): V? {
        return key.lowercase().let { k ->
            (floorKey(k)?.lowercase() to ceilingKey(k)?.lowercase()).let { (before, after) ->
                when {
                    before == null -> after
                    after == null -> before
                    before.contains(k) -> before
                    after.contains(k) -> after
                    else -> if(abs(k.compareTo(before)) < abs(k.compareTo(after))) { before } else { after }
                }
            }.let {
                get(it)
            }
        }
    }
}
