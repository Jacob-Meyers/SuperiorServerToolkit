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

public class PayMoneyCommand implements CommandExecutor, TabCompleter {

    private final BalanceManager balanceManager;

    public PayMoneyCommand(SupToolkit plugin) {
        this.balanceManager = plugin.getBalanceManager();
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("§cInvalid argument; Usage: /<command> <player> <amount>");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayerExact(args[0]);
        if (targetPlayer == null) {
            player.sendMessage("§cPlayer not found!");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid number!");
            return true;
        }

        if (player == targetPlayer) {
            player.sendMessage("§cCannot send money to yourself!");
            return true;
        }

        if (amount > balanceManager.getBalance(player.getUniqueId())) {
            player.sendMessage("§cInvalid amount, you're too broke for that :(");
            return true;
        }

        if (amount <= 0 || amount > 5_000_000) {
            player.sendMessage("§cInvalid amount, range §l$1 - $5,000,000!");
            return true;
        }

        ConsoleCommandSender console = Bukkit.getConsoleSender();

        // Add money
        UUID uuid = targetPlayer.getUniqueId();
        balanceManager.transfer(player.getUniqueId(), uuid, amount);

        // Messages
        player.sendMessage("§aPaid §b" + targetPlayer.getName() + "§a §l$" + BalanceManager.formatMoney(amount) + "§r§a to " + targetPlayer.getName());
        console.sendMessage(player + " paid " + targetPlayer.getName() + " $" + BalanceManager.formatMoney(amount) + " to " + targetPlayer.getName());
        targetPlayer.sendMessage("§aYou received §l$" + BalanceManager.formatMoney(amount) + " from " + player.getName());


        // Update Scoreboard
        PersonalBalanceScoreboard scoreboard = new PersonalBalanceScoreboard(balanceManager);
        scoreboard.show(targetPlayer);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        List<String> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList());

        onlinePlayers.remove(sender.getName());

        if (args.length == 1)
            return onlinePlayers;
        else
            return new ArrayList<>();
    }
}
