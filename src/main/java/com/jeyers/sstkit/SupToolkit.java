package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
public final class SupToolkit extends JavaPlugin implements Listener {

    BalanceManager balanceManager;

    static String CURRENT_VERSION_PREFIX = "v"; // Prefix to add beofre the modrinth current version_number
    static String CURRENT_VERSION_NUMBER; // IS SET DURING onEnable()
    final static String MODRINTH_PROJECT_ID = "FfmaCyRL";
    static String serverNameScoreboard;
    static boolean scoreboardEnabled;

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
            "vote",
            "tpa",
            "tpacancel",
            "tpyes",
            "tpno",
            "sethome",
            "home",
            "balance",
            "addmoney",
            "pay",
            "sell",
            "trade"
    ));

    private File balancesFile;
    private FileConfiguration balancesConfig;

    private void setupBalancesFile() {
        balancesFile = new File(getDataFolder(), "balances.yml");

        if (!balancesFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            balancesFile.getParentFile().mkdirs();
            saveResource("balances.yml", false); // optional (only if you include one in jar)
        }

        balancesConfig = YamlConfiguration.loadConfiguration(balancesFile);
    }

    public FileConfiguration getBalancesConfig() {
        return balancesConfig;
    }

    public BalanceManager getBalanceManager() {
        return balanceManager;
    }

    public void saveBalances() {
        try {
            balancesConfig.save(balancesFile);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        double bal = balanceManager.loadFromStorage(uuid);
        balanceManager.loadBalance(uuid, bal);

        PersonalBalanceScoreboard scoreboard = new PersonalBalanceScoreboard(balanceManager);
        scoreboard.show(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        balanceManager.save(uuid);
        balanceManager.unload(uuid);
    }


    static long COMBAT_TIME_TPA;
    static long COMBAT_TIME_HOME;
    static long COMBAT_TIME_WARP;
    static int USE_DIAMETER_TPA;
    static int USE_DIAMETER_HOME;

    private void registerCommand(String name, CommandExecutor executor, TabCompleter tabCompleter) {
        PluginCommand command = getCommand(name);
        if (command != null) {
            command.setExecutor(executor);
            if (tabCompleter != null) {
                command.setTabCompleter(tabCompleter);
            }
        } else {
            getLogger().warning("Failed to register command '" + name + "' because it is missing from plugin.yml!");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        setupBalancesFile();
        balanceManager = new BalanceManager(this);
        int ticks = 5 * 60 * 20; // 5 minutes * 60 seconds * 20 ticks
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            balanceManager.saveAll();
            saveBalances();
        }, ticks, ticks);

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        CURRENT_VERSION_NUMBER = getDescription().getVersion();
        serverNameScoreboard = getConfig().getString("scoreboard.serverName");
        scoreboardEnabled = getConfig().getBoolean("scoreboard.show");
        COMBAT_TIME_TPA = getConfig().getInt("tpa.pvpTimer")* 1000L;
        COMBAT_TIME_WARP = getConfig().getInt("warp.pvpTimer")* 1000L;
        COMBAT_TIME_HOME = getConfig().getInt("home.pvpTimer")* 1000L;
        USE_DIAMETER_TPA = getConfig().getInt("tpa.useDiameter");
        USE_DIAMETER_HOME = getConfig().getInt("home.useDiameter");

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(), this);
        getServer().getPluginManager().registerEvents(this, this);


        /*
        REGISTER ALL COMMANDS
         */
        CommandRegistry registry = new CommandRegistry(this);
        Commands.registerAll(this, registry);

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
            if (Float.parseFloat(CURRENT_VERSION_NUMBER) < Float.parseFloat(mgInfo.version_number.replace(CURRENT_VERSION_PREFIX, "")) && mgInfo.version_type.equals("release")) {
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
        balanceManager.saveAll();
        saveBalances();
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
