package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class SupToolkit extends JavaPlugin implements Listener {

    public final static List<String> commandList = new ArrayList<>(Arrays.asList(
"sstkitcommands",
"sstkitreload / sstkit / reloadsstkit",
"readptransform / playertransform",
"broadcastmsg",
"kickuntilrestart",
"kickuntilrestartclear",
"fly",
"giveperm",
"ghost",
"warpcreate",
"warpremove",
"warp",
"memoryusage / mem",
"cpuusage / cpu",
"heal",
"list",
"invsee",
"endersee",
"invincible",
"vpncheck",
"tempban",
"tempbanlist",
"untempban / pardontempban",
"vote"
    ));

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(
                getCommand("sstkitcommands"))
                .setExecutor(new CommandsListCommand());
        Objects.requireNonNull(
                getCommand("sstkitreload"))
                .setExecutor(new ReloadConfigCommand(this));
                Objects.requireNonNull(getCommand("sstkitreload")).setTabCompleter(this);
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
        Objects.requireNonNull(
                getCommand("invsee"))
                .setExecutor(new InvSeeCommand());
                Objects.requireNonNull(getCommand("invsee")).setTabCompleter(this);
        Objects.requireNonNull(
                getCommand("invincible"))
                .setExecutor(new InvincibleCommand());
        Objects.requireNonNull(
                getCommand("vpncheck"))
                .setExecutor(new VPNCheckCommand(this));
        Objects.requireNonNull(
                getCommand("tempban"))
                .setExecutor(new TempBanCommand(this));
                Objects.requireNonNull(getCommand("tempban")).setTabCompleter(this);
        Objects.requireNonNull(
                getCommand("vote"))
                .setExecutor(new VoteCommand(this));
                Objects.requireNonNull(getCommand("vote")).setTabCompleter(this);

        /// PluginCommand cmd = getCommand("warp");
        /// if (cmd != null) {
        ///     cmd.setPermission("none.permission.node");
        /// }

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        List<String> commands = getConfig().getStringList("disabledFeatures.commands");
        for (String command : commands) {
            PluginCommand cmd = getCommand(command);
            if (cmd != null) {
                cmd.setPermission("disabled.command");
                console.sendMessage("§e[Superior Server Toolkit]§b Command '" + command + "' is now disabled.");
            } else {
                console.sendMessage("§e[Superior Server Toolkit]§c Command not found '" + command + "' » Unabled to disable.");
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (InvincibleCommand.invinciblePlayers.contains(player.getUniqueId())) {
                // Preventing any damage
                event.setCancelled(true);
            }
        }
    }

}
