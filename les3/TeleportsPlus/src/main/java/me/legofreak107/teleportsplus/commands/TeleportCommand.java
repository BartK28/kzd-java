package me.legofreak107.teleportsplus.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cJe moet een speler zijn om dit commando uit te voeren!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length != 3) {
            player.sendMessage(Component.text("§cOnvoldoende argumenten. Gebruik: §7/tp <x> <y> <z>"));
            return false;
        }
        try {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            Location location = new Location(player.getLocation().getWorld(), x, y, z);
            player.teleport(location);
            player.sendMessage(Component.text("§eTeleported!"));
        } catch (Exception e) {
            player.sendMessage(Component.text("§cCoordinaten moeten cijfers zijn."));
            return false;
        }
        return false;
    }
}
