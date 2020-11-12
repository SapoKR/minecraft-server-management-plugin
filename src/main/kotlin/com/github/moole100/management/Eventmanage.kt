package com.github.moole100.management

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import java.awt.Color
import java.util.Calendar
import kotlin.random.Random


class Eventmanage : Listener{
    @EventHandler
    fun onPaperServerListPing(event: PaperServerListPingEvent) {
        val c = Calendar.getInstance()
        event.numPlayers = c.get(Calendar.YEAR) * 7 + (c.get(Calendar.MONTH) + 5) * 26 + c.get(Calendar.DAY_OF_MONTH)
        event.maxPlayers = c.get(Calendar.HOUR) * 7 + c.get(Calendar.MINUTE) * 5 + c.get(Calendar.SECOND) + 26
        event.motd = "${net.md_5.bungee.api.ChatColor.of(Color(Random.nextInt(0x92ddc8)))}${net.md_5.bungee.api.ChatColor.BOLD}${Bukkit.getServer().motd}"
        event.playerSample.clear()
    }
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        try {
            event.player.teleport(event.player.bedLocation)
        } catch (e : IllegalStateException) {
            event.player.teleport(event.player.world.spawnLocation)
        }
    }
    @EventHandler
    fun onQuit(event:PlayerQuitEvent){
        event.quitMessage = "${ChatColor.YELLOW}Bye Bye. ${event.player.name} See you again soon."
    }
    @EventHandler
    fun onDead(event: PlayerDeathEvent){
        event.deathMessage = "${ChatColor.AQUA}${event.entity.name} is Dead."
    }
    @EventHandler
    fun onJoin(event:PlayerJoinEvent){
        event.joinMessage = "${ChatColor.YELLOW}Welcome to the ${Bukkit.getServer().name}."
    }

}
