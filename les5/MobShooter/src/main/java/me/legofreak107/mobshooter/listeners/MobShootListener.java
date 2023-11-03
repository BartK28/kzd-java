package me.legofreak107.mobshooter.listeners;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class MobShootListener implements Listener {

    @EventHandler
    public void onShoot(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR) {
            // Event is not left click air
            return;
        }
        Player player = event.getPlayer();
        // Check if player is holding a stick
        if (player.getInventory().getItemInMainHand().getType() != Material.STICK) {
            // Player is not holding a stick
            return;
        }

        // Spawn a sheep at the players location
        Sheep sheep = player.getWorld().spawn(player.getLocation(), Sheep.class);

        // Make new random
        Random random = new Random();

        // Set the sheeps velocity to match the players direction. And multiply it to shoot.
        sheep.setVelocity(player.getLocation().getDirection().multiply((random.nextInt(10) / 2f) + 2));

        // Set the sheeps color to a random color
        sheep.setColor(DyeColor.values()[random.nextInt(DyeColor.values().length)]);
    }

}
