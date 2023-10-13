package me.legofreak107.teleportsplus.commands;

import me.legofreak107.teleportsplus.TeleportsPlus;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cJe moet een speler zijn om dit commando uit te voeren!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Component.text("§eJe kan kiezen uit de volgende warps:"));
            if (!TeleportsPlus.getWarpConfig().contains("warps")) {
                player.sendMessage(Component.text("§7Geen warps gespecificeerd. Maak er een met §e/setwarp"));
                return false;
            }
            for (String warp : TeleportsPlus.getWarpConfig().getConfigurationSection("warps").getKeys(false)) {
                player.sendMessage(Component.text("§7 - §e" + warp));
            }
            return false;
        }
        String warpName = args[0];
        if (!TeleportsPlus.getWarpConfig().contains("warps." + warpName)) {
            player.sendMessage(Component.text("§cDeze warp bestaat niet!"));
            return false;
        }
        Location warpLocation = TeleportsPlus.getWarpConfig().getLocation("warps." + warpName);
        player.teleport(warpLocation);
        player.sendMessage(Component.text("§eWarped!"));
        return false;
    }

}
