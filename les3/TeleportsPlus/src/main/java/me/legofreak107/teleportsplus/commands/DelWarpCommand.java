package me.legofreak107.teleportsplus.commands;

import me.legofreak107.teleportsplus.TeleportsPlus;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cJe moet een speler zijn om dit commando uit te voeren!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(Component.text("§cJe moet de naam van de warp meegeven! Gebruik §7/setwarp <naam>"));
            return false;
        }
        String warpName = args[0];
        if (!TeleportsPlus.getWarpConfig().contains("warps." + warpName)) {
            player.sendMessage(Component.text("§cDeze warp bestaat niet!"));
            return false;
        }
        TeleportsPlus.getWarpConfig().set("warps." + warpName, null);
        TeleportsPlus.getInstance().saveConfig(TeleportsPlus.getWarpConfig(), "warps.yml");
        player.sendMessage(Component.text("§eWarp §7"+warpName+" §everwijdert!"));
        return false;
    }

}
