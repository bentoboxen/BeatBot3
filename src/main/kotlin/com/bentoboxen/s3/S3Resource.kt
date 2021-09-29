package com.bentoboxen.s3

import java.util.*

data class S3Resource(
    val key: String,
    val buffer: ByteArray? = null,
    val size: Long? = null,
    val contentType: String? = null,
    val timestamp: Date? = null
)
