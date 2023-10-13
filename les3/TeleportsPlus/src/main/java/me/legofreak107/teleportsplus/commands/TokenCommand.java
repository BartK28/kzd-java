package me.legofreak107.teleportsplus.commands;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BaseDVFactory;
import me.legofreak107.teleportsplus.TeleportsPlus;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TokenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cJe moet een speler zijn om dit commando uit te voeren!");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            int tokens = 0;
            if (TeleportsPlus.getTokenConfig().contains(player.getUniqueId().toString())) {
                tokens = TeleportsPlus.getTokenConfig().getInt(player.getUniqueId().toString());
            }
            player.sendMessage(Component.text("§eJouw beschikbare tokens: §7" + tokens));
            return false;
        }
        String subCommand = args[0];
        if (subCommand.equalsIgnoreCase("add")) {
            if (args.length != 3) {
                player.sendMessage(Component.text("§cGebruik: §7/tokens add <player> <amount>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Component.text("§cDeze speler is niet online!"));
                return false;
            }
            try {
                int tokens = Integer.parseInt(args[2]);
                int curTokens = 0;
                if (TeleportsPlus.getTokenConfig().contains(target.getUniqueId().toString())) {
                    curTokens = TeleportsPlus.getTokenConfig().getInt(target.getUniqueId().toString());
                }
                TeleportsPlus.getTokenConfig().set(target.getUniqueId().toString(), curTokens + tokens);
                TeleportsPlus.getInstance().saveConfig(TeleportsPlus.getTokenConfig(), "tokens.yml");
                target.sendMessage(Component.text("§eEr zijn §7" + tokens + " §etoegevoegd aan je account! Nieuw token aantal: §7" + (curTokens + tokens)));
                player.sendMessage(Component.text("§eTokens toegevoegd!"));
                return true;
            } catch (Exception e) {
                player.sendMessage(Component.text("§cToken hoeveelheid moet een nummer zijn!"));
                return false;
            }
        }
        if (subCommand.equalsIgnoreCase("get")) {
            if (args.length != 2) {
                player.sendMessage(Component.text("§cGebruik: §7/tokens get <player>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Component.text("§cDeze speler is niet online!"));
                return false;
            }
            int curTokens = 0;
            if (TeleportsPlus.getTokenConfig().contains(target.getUniqueId().toString())) {
                curTokens = TeleportsPlus.getTokenConfig().getInt(target.getUniqueId().toString());
            }
            player.sendMessage(Component.text("§7" + target.getName() + "§e heeft §7" + curTokens + "§e tokens!"));
            return false;
        }
        if (subCommand.equalsIgnoreCase("set")) {
            if (args.length != 3) {
                player.sendMessage(Component.text("§cGebruik: §7/tokens set <player> <amount>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Component.text("§cDeze speler is niet online!"));
                return false;
            }
            try {
                int tokens = Integer.parseInt(args[2]);
                TeleportsPlus.getTokenConfig().set(target.getUniqueId().toString(), tokens);
                TeleportsPlus.getInstance().saveConfig(TeleportsPlus.getTokenConfig(), "tokens.yml");
                target.sendMessage(Component.text("§eTokens aangepast naar: §7" + tokens));
                player.sendMessage(Component.text("§eTokens aangepast!"));
                return true;
            } catch (Exception e) {
                player.sendMessage(Component.text("§cToken hoeveelheid moet een nummer zijn!"));
                return false;
            }
        }
        if (subCommand.equalsIgnoreCase("take")) {

            if (args.length != 3) {
                player.sendMessage(Component.text("§cGebruik: §7/tokens take <player> <amount>"));
                return false;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(Component.text("§cDeze speler is niet online!"));
                return false;
            }
            try {
                int tokens = Integer.parseInt(args[2]);
                int curTokens = 0;
                if (TeleportsPlus.getTokenConfig().contains(target.getUniqueId().toString())) {
                    curTokens = TeleportsPlus.getTokenConfig().getInt(target.getUniqueId().toString());
                }
                TeleportsPlus.getTokenConfig().set(target.getUniqueId().toString(), curTokens - tokens);
                TeleportsPlus.getInstance().saveConfig(TeleportsPlus.getTokenConfig(), "tokens.yml");
                target.sendMessage(Component.text("§eEr zijn §7" + tokens + " §eweggehaald van je account! Nieuw token aantal: §7" + (curTokens - tokens)));
                player.sendMessage(Component.text("§eTokens weggehaald!"));
                return true;
            } catch (Exception e) {
                player.sendMessage(Component.text("§cToken hoeveelheid moet een nummer zijn!"));
                return false;
            }
        }

        player.sendMessage(Component.text("§cFoutief subcommando, gebruik:"));
        player.sendMessage(Component.text("§c- §7/tokens add"));
        player.sendMessage(Component.text("§c- §7/tokens get"));
        player.sendMessage(Component.text("§c- §7/tokens set"));
        player.sendMessage(Component.text("§c- §7/tokens take"));
        return false;
    }
}
