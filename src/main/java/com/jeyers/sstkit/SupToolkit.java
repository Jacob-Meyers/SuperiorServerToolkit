package com.jeyers.sstkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class SupToolkit extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("readptransform").setExecutor(new ReadPTransformCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
