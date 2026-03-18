package com.jeyers.sstkit;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SupToolkit extends JavaPlugin {

    public final static List<String> commandList = new ArrayList<>(Arrays.asList(
            "sstkitcommands",
            "readptransform / playertransform",
            "broadcastmsg",
            "kickuntilrestart",
            "kickuntilrestartclear",
            "fly",
            "giveperm",
            "ghost"
    ));


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        Objects.requireNonNull(
                getCommand("sstkitcommands"))
                .setExecutor(new CommandsListCommand());
        Objects.requireNonNull(
                getCommand("readptransform"))
                .setExecutor(new ReadPTransformCommand());
        Objects.requireNonNull(
                getCommand("broadcastmsg"))
                .setExecutor(new BroadcastCommand());
        Objects.requireNonNull(
                getCommand("kickuntilrestart"))
                .setExecutor(new KickUntilRestart());
        Objects.requireNonNull(
                getCommand("kickuntilrestartclear"))
                .setExecutor(new KickUntilRestartClear());
        Objects.requireNonNull(
                getCommand("fly"))
                .setExecutor(new FlyCommand());
        Objects.requireNonNull(
                getCommand("giveperm"))
                .setExecutor(new GivePermCommand(this));
        Objects.requireNonNull(
                getCommand("ghost"))
                .setExecutor(new GhostCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
