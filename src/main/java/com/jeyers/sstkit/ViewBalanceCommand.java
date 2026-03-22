package com.jeyers.sstkit;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.*;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/21/2026
/// Last Edit    3/21/2026
///

public class ViewBalanceCommand implements CommandExecutor {

    private final BalanceManager balanceManager;

    public ViewBalanceCommand(SupToolkit plugin) {
        this.balanceManager = plugin.getBalanceManager();
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(Double.toString(balanceManager.getBalance(player.getUniqueId())));
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid balance!");
            return true;
        }
        // View money
        player.sendMessage("§aYour Balance: §l$" + BalanceManager.formatMoney(amount));

        // Update Scoreboard
        PersonalBalanceScoreboard scoreboard = new PersonalBalanceScoreboard(balanceManager);
        scoreboard.show(player);

        return true;
    }
}
