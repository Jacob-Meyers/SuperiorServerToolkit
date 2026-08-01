package com.jeyers.sstkit;

import org.bukkit.plugin.java.JavaPlugin;

public class Commands {

    public static void registerAll(JavaPlugin plugin, CommandRegistry registry) {
        registry.register("sstkitcommands", new CommandsListCommand());
        registry.register("sstkitreload", new ReloadConfigCommand(plugin));
        registry.register("readptransform", new ReadPTransformCommand());
        registry.register("broadcastmsg", new BroadcastCommand());
        registry.register("kickuntilrestart", new KickUntilRestartCommand());
        registry.register("kickuntilrestartclear", new KickUntilRestartClearCommand());
        registry.register("fly", new FlyCommand());
        registry.register("ghost", new GhostCommand());

        registry.register("warpcreate", new WarpCreateCommand(plugin));
        registry.register("warpremove", new WarpRemoveCommand(plugin));
        registry.register("warplist", new WarpListCommand(plugin));
        registry.register("warp", new WarpCommand(plugin));

        registry.register("memoryusage", new MemoryUsageCommand());
        registry.register("cpuusage", new CPUUsageCommand());
        registry.register("heal", new HealCommand());
        registry.register("list", new ListCommand(plugin));

        registry.register("invsee", new InvSeeCommand());
        registry.register("invincible", new InvincibleCommand());
        registry.register("vpncheck", new VPNCheckCommand(plugin));
        registry.register("tempban", new TempBanCommand(plugin));
        registry.register("vote", new VoteCommand(plugin));

        TPACommand tpaCommand = new TPACommand();
        registry.register("tpa", tpaCommand);
        registry.register("tpyes", tpaCommand);
        registry.register("tpno", tpaCommand);

        registry.register("sethome", new SetHomeCommand(plugin));
        registry.register("home", new HomeCommand(plugin));

        registry.register("balance", new ViewBalanceCommand((SupToolkit) plugin));
        registry.register("addmoney", new AddMoneyCommand((SupToolkit) plugin));
        registry.register("pay", new PayMoneyCommand((SupToolkit) plugin));
        registry.register("sell", new SellCommand((SupToolkit) plugin));
        registry.register("trade", new TradeCommand((SupToolkit) plugin));
    }
}