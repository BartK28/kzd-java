package me.legofreak107.mobshooter;

import me.legofreak107.mobshooter.listeners.MobShootListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobShooter extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new MobShootListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
