package com.jeyers.sstkit;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SupToolkit extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(
                getCommand("readptransform"))
                .setExecutor(new ReadPTransformCommand());
        Objects.requireNonNull(
                getCommand("broadcastmsg"))
                .setExecutor(new BroadcastCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
