package com.github.moole100.management

import io.ktor.application.install
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class ServerManagementPlugin : JavaPlugin() {
    override fun onEnable() {
        server.scheduler.runTaskTimer(this, Restarter(), 20L * 60L, 20L * 60L)
        Bukkit.getScheduler().runTaskTimer(this@ServerManagementPlugin,Runnable {
            Bukkit.broadcastMessage("==========================================")
            Bukkit.broadcastMessage("제작자")
            Bukkit.broadcastMessage("PatrickKR : https://github.com/patrick-mc")
            Bukkit.broadcastMessage("moole100  : https://github.com/moole100")
            Bukkit.broadcastMessage("==========================================")
        },0L, 72000)
        getPluginManager().registerEvents(Eventmanage(),this)
        embeddedServer(Netty, 8080) {
            install(WebSockets)
            routing {
                webSocket("/commands") {
                    while (true) {
                        when (val frame = incoming.receive()) {
                            is Frame.Text -> {
                                val text = frame.readText()
                                Bukkit.getScheduler().runTask(this@ServerManagementPlugin, Runnable {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text)
                                })
                            }
                        }
                    }
                }
            }
        }.start()
    }
    override fun onDisable() {
        logger.info("Disable Server Management.")
    }
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return true
    }
}
class Restarter: Runnable {
    private val time = System.currentTimeMillis()
    override fun run() {
        val elapsedTime = System.currentTimeMillis() - time
        val restartTime = 1000L * 60L * 60L * 2L
        if (elapsedTime >= restartTime) {
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage("${ChatColor.GOLD}Server restarting...")
            }
            Bukkit.shutdown()
        } else if (elapsedTime == restartTime - 60000L) {
            Bukkit.broadcastMessage("${ChatColor.GOLD}약 1분 뒤 서버가 재시작됩니다.")
        }
    }
}