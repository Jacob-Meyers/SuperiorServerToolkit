package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jspecify.annotations.NonNull;

import java.util.*;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/21/2026
/// Last Edit    3/21/2026
///

public class SellCommand implements CommandExecutor {

    private final BalanceManager balanceManager;
    private final SupToolkit plugin;

    public SellCommand(SupToolkit plugin) {
        this.balanceManager = plugin.getBalanceManager();
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }


        // Add money
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem != null && heldItem.getType() != Material.AIR) {
            String itemName = heldItem.getType().toString();
            int itemAmount = heldItem.getAmount();

            if (plugin.getConfig().contains("sellvalues."+itemName.toLowerCase())) {
                double amount = plugin.getConfig().getDouble("sellvalues."+itemName.toLowerCase())*itemAmount;
                clearFromSlot(player, heldItem.getType(), itemAmount);
                balanceManager.addBalance(player.getUniqueId(), amount);

                ConsoleCommandSender console = Bukkit.getConsoleSender();
                player.sendMessage("§aSold " + itemName.replaceAll("_", " ") + " x" + itemAmount + " for §l$" + amount);
                console.sendMessage(player.getName() + " sold " + itemName.replaceAll("_", " ") + " x" + itemAmount + " for $" + amount);
            } else {
                player.sendMessage("§cItem " + itemName.replaceAll("_", " ") + " cannot be sold :(");
            }
        } else {
            player.sendMessage("§cYou are holding nothing in your mainhand.");
        }


        // Update Scoreboard
        PersonalBalanceScoreboard scoreboard = new PersonalBalanceScoreboard(balanceManager);
        scoreboard.show(player);

        return true;
    }

    public void clearFromSlot(Player player, Material materialToClear, int amountToClear) {
        PlayerInventory inventory = player.getInventory();
        int remaining = amountToClear;

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack == null) continue;

            if (stack.getType() == materialToClear) {
                if (stack.getAmount() <= remaining) {
                    remaining -= stack.getAmount();
                    inventory.setItem(i, null);
                } else {
                    stack.setAmount(stack.getAmount() - remaining);
                    inventory.setItem(i, stack);
                    break;
                }
            }

            if (remaining <= 0) break;
        }
    }
}
