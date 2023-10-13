package me.legofreak107.teleportsplus.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class TrampolineListener implements Listener {

    private Material trampolineMaterial = Material.SLIME_BLOCK;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // De speler die beweegt
        Player player = event.getPlayer();

        // De locatie van de speler -1 y as (1 blok onder de speler)
        Location location = player.getLocation().add(0, -1, 0);

        // Check of de speler op een trampoline staat
        if (location.getBlock().getType() == trampolineMaterial) {
            // Zet de velocity (momentum) van de speler naar x = 0, y = 2, z = 0

            player.setVelocity(new Vector(0,2,0));
        }
    }
}
