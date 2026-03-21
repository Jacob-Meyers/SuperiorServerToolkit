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

    static String CURRENT_VERSION_PREFIX = "v"; // Prefix to add beofre the modrinth current version_number
    static String CURRENT_VERSION_NUMBER; // IS SET DURING onEnable()
    final static String MODRINTH_PROJECT_ID = "FfmaCyRL";

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

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        CURRENT_VERSION_NUMBER = CURRENT_VERSION_PREFIX + getDescription().getVersion();

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(), this);
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

        // ALL /tpa COMMANDS ARE IN ONE CLASS BUT HAVE NO ALIASES TO -
        // EACHOTHER TO ALLOW DISABLING OF SPECIFIC TPA COMMANDS IN THE CONFIG.
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new TPACommand()); Objects.requireNonNull(getCommand("tpa")).setTabCompleter(this);
        Objects.requireNonNull(getCommand("tpyes")).setExecutor(new TPACommand()); Objects.requireNonNull(getCommand("tpyes")).setTabCompleter(this);
        Objects.requireNonNull(getCommand("tpno")).setExecutor(new TPACommand()); Objects.requireNonNull(getCommand("tpno")).setTabCompleter(this);

        Objects.requireNonNull(
                getCommand("sethome"))
                .setExecutor(new SetHomeCommand(this));
                Objects.requireNonNull(getCommand("sethome")).setTabCompleter(this);
        Objects.requireNonNull(
                getCommand("home"))
                .setExecutor(new HomeCommand(this));
                Objects.requireNonNull(getCommand("home")).setTabCompleter(this);

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

        try {
            CheckForUpdate.modrinthGETinfo mgInfo = CheckForUpdate.checkForUpdate();
            assert mgInfo != null;
            if (!CURRENT_VERSION_NUMBER.equals(mgInfo.version_number) && mgInfo.version_type.equals("release")) {
                console.sendMessage("§e[Superior Server Toolkit]§a SUPPERIOR SERVER TOOLKIT HAS AN UPDATE FOR THIS MINECRAFT VERSION !!!");
                for (CheckForUpdate.modrinthGETFileInfo file : mgInfo.files) {
                    if (file.primary) {
                        console.sendMessage("§e[Superior Server Toolkit]§a NEW VERSION URL :» §b" + file.url);
                        break;
                    } else {
                        console.sendMessage("§e[Superior Server Toolkit]§c NEW UPDATE DETECTED, BUT URL FAILED TO GET; CHECK MODRINTH FOR NEW RELEASES");
                    }
                }

            }
        } catch (Exception ignored) {
            console.sendMessage("§e[Superior Server Toolkit]§c Failed to check for new updates. Possible internet connectivity issues?");
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
