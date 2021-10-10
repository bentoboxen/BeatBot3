package com.bentoboxen

import com.bentoboxen.bot.*
import com.bentoboxen.bot.Bot
import com.bentoboxen.s3.S3ResourceManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun main(vararg params: String) {
    S3ResourceManager.init()
    Config.config(*params).let { config ->
        joinAll(
            GlobalScope.launch {
                Bot.create(
                    name = "se",
                    token = config.soundEffectBotToken,
                    ConnectSoundEffectCommand,
                    DisconnectCommand,
                    QuitCommand,
                    PlayCommand,
                    PingCommand,
                    StopCommand
                ).start()
            },
            GlobalScope.launch {
                delay(1000)
                Bot.create(
                    name = "bg",
                    token = config.backGroundBotToken,
                    ConnectBackgroundCommand,
                    DisconnectCommand,
                    QuitCommand,
                    ListCommand,
                    BackGroundCommand,
                    PingCommand,
                    StopCommand
                ).start()
            }
        )
    }
}