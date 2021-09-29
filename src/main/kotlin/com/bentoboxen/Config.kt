package com.bentoboxen

import mu.KLogging

data class Config(
    val backGroundBotToken: String,
    val soundEffectBotToken: String
) {
    companion object : KLogging() {
        fun config(vararg params: String): Config =
            params.toList().map {
                it.split("=")
            }.associate {
                it[0] to it[1]
            }.let {
                it.forEach { e ->
                    logger.debug("config ${e.key} = ${e.value}")
                }
                Config(
                    backGroundBotToken = it["backGroundBotToken"] ?: "",
                    soundEffectBotToken = it["soundEffectBotToken"] ?: ""
                )
            }
        }
}


