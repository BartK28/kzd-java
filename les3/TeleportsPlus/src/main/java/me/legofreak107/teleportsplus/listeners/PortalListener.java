package me.legofreak107.teleportsplus.listeners;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.legofreak107.teleportsplus.objects.Portal;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PortalListener implements Listener {

    private List<Portal> portals = new ArrayList<>();

    public void register(Portal portal) {
        portals.add(portal);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        // De speler die beweegt
        Player player = event.getPlayer();

        // De locatie van de speler
        Location location = player.getLocation();

        // Loop door alle portals
        for (Portal portal : portals) {

            // Loop door alle blocks in de portal
            for (Location portalLocation : portal.getBlocks()) {

                // Check of de speler in de portal staat
                if (portalLocation.getBlockX() == location.getBlockX()
                        && portalLocation.getBlockY() == location.getBlockY()
                        && portalLocation.getBlockZ() == location.getBlockZ()
                        && portalLocation.getWorld() == location.getWorld()) {
                    player.teleport(portal.getTarget());
                }
            }
        }
    }
}
