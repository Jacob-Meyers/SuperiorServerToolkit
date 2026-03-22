package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.*;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/21/2026
/// Last Edit    3/21/2026
///

public class AddMoneyCommand implements CommandExecutor {

    private final BalanceManager balanceManager;

    public AddMoneyCommand(SupToolkit plugin) {
        this.balanceManager = plugin.getBalanceManager();
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (args.length != 2) {
            sender.sendMessage("§cInvalid argument; Usage: /<command> <player> <amount>");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayerExact(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage("§cPlayer not found!");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid number!");
            return true;
        }

        if (amount > 1_000_000_000) {
            sender.sendMessage("§cInvalid amount, max 1,000,000,000!");
            return true;
        }

        // Add money
        UUID uuid = targetPlayer.getUniqueId();
        balanceManager.addBalance(uuid, amount);


        ConsoleCommandSender console = Bukkit.getConsoleSender();

        // Messages
        sender.sendMessage("§aAdded §l$" + BalanceManager.formatMoney(amount) + "§r§a to " + targetPlayer.getName());
        console.sendMessage(sender.getName() + " added $" + BalanceManager.formatMoney(amount) + " to " + targetPlayer.getName());
        targetPlayer.sendMessage("§aYou received §l$" + BalanceManager.formatMoney(amount));


        // Update Scoreboard
        PersonalBalanceScoreboard scoreboard = new PersonalBalanceScoreboard(balanceManager);
        scoreboard.show(targetPlayer);

        return true;
    }
}
