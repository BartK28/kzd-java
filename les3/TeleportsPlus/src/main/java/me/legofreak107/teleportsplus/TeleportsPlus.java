package me.legofreak107.teleportsplus;

import lombok.Getter;
import me.legofreak107.teleportsplus.commands.*;
import me.legofreak107.teleportsplus.listeners.PortalListener;
import me.legofreak107.teleportsplus.listeners.TrampolineListener;
import me.legofreak107.teleportsplus.objects.Portal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class TeleportsPlus extends JavaPlugin {

    @Getter
    private static TeleportsPlus instance;
    @Getter
    private static FileConfiguration warpConfig;
    @Getter
    private static FileConfiguration tokenConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("tokens").setExecutor(new TokenCommand());
        warpConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/warps.yml"));
        tokenConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/tokens.yml"));

        PortalListener portalListener = new PortalListener();
        Bukkit.getPluginManager().registerEvents(portalListener, this);

        Portal portal = new Portal(
                new Location(Bukkit.getWorld("world"), 0, 100, 0),
                Arrays.asList(
                        new Location(Bukkit.getWorld("world"), 0, 50, 0),
                        new Location(Bukkit.getWorld("world"), 0, 50, 1),
                        new Location(Bukkit.getWorld("world"), 0, 51, 0),
                        new Location(Bukkit.getWorld("world"), 0, 51, 1)
                ));
        portalListener.register(portal);

        TrampolineListener trampolineListener = new TrampolineListener();
        Bukkit.getPluginManager().registerEvents(trampolineListener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void saveConfig(FileConfiguration config, String name) {
        File configFile = new File(getDataFolder() + "/" + name);
        try{
            if (!configFile.exists()) {
                getDataFolder().mkdirs();
                configFile.createNewFile();
            }
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
