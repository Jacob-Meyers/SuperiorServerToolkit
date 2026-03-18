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
            "ghost",
            "warpcreate",
            "warpremove",
            "warp"
    ));


    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

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
                .setExecutor(new KickUntilRestartCommand());
        Objects.requireNonNull(
                getCommand("kickuntilrestartclear"))
                .setExecutor(new KickUntilRestartClearCommand());
        Objects.requireNonNull(
                getCommand("fly"))
                .setExecutor(new FlyCommand());
        Objects.requireNonNull(
                getCommand("giveperm"))
                .setExecutor(new GivePermCommand(this));
        Objects.requireNonNull(
                getCommand("ghost"))
                .setExecutor(new GhostCommand());
        Objects.requireNonNull(
                getCommand("warpcreate"))
                .setExecutor(new WarpCreateCommand(this));
                Objects.requireNonNull(getCommand("warpcreate")).setTabCompleter(this);
        Objects.requireNonNull(
                getCommand("warpremove"))
                .setExecutor(new WarpRemoveCommand(this));
                Objects.requireNonNull(getCommand("warp")).setTabCompleter(this);
        Objects.requireNonNull(
                getCommand("warplist"))
                .setExecutor(new WarpListCommand(this));
        Objects.requireNonNull(
                getCommand("warp"))
                .setExecutor(new WarpCommand(this));
                Objects.requireNonNull(getCommand("warp")).setTabCompleter(this);
        Objects.requireNonNull(
                getCommand("memoryusage"))
                .setExecutor(new MemoryUsageCommand());
        Objects.requireNonNull(
                getCommand("cpuusage"))
                .setExecutor(new CPUUsageCommand());
        Objects.requireNonNull(
                getCommand("heal"))
                .setExecutor(new HealCommand());
        Objects.requireNonNull(
                getCommand("list"))
                .setExecutor(new ListCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
