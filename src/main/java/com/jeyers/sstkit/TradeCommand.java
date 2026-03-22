package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.*;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/21/2026
/// Last Edit    3/21/2026
///

// This was a pain in the ass.

public class TradeCommand implements CommandExecutor, TabCompleter {
    private final BalanceManager balanceManager;

    // Stores active trade offers: target -> TradeOffer
    private final HashMap<UUID, TradeOffer> tradeRequests = new HashMap<>();

    public TradeCommand(SupToolkit plugin) {
        this.balanceManager = plugin.getBalanceManager();
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {

        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("§cUsage: /trade <player> <money> <item> <amount> OR /trade accept");
            return true;
        }

        if (args[0].equalsIgnoreCase("accept")) {
            TradeOffer offer = tradeRequests.get(player.getUniqueId());
            if (offer == null) {
                player.sendMessage("§cNo trade request to accept.");
                return true;
            }

            Player senderPlayer = Bukkit.getPlayer(offer.senderUUID);
            if (senderPlayer == null) {
                player.sendMessage("§cThe player who sent the trade is offline.");
                tradeRequests.remove(player.getUniqueId());
                return true;
            }

            if (!hasItem(player, offer.requestedItem, offer.requestedAmount)) {
                player.sendMessage("§cYou don't have enough " + offer.requestedItem.name() + " to complete the trade.");
                return true;
            }

            if (!balanceManager.has(senderPlayer.getUniqueId(), offer.moneyAmount)) {
                player.sendMessage("§cThe sender does not have enough money.");
                tradeRequests.remove(player.getUniqueId());
                return true;
            }

            balanceManager.transfer(senderPlayer.getUniqueId(), player.getUniqueId(), offer.moneyAmount);

            removeItem(player, offer.requestedItem, offer.requestedAmount);
            senderPlayer.getInventory().addItem(new ItemStack(offer.requestedItem, offer.requestedAmount));

            player.sendMessage("§aTrade completed! You received §l$" + offer.moneyAmount + "§r§a and sent " +
                    offer.requestedAmount + " " + offer.requestedItem.name());
            senderPlayer.sendMessage("§aTrade completed! You sent §l$" + offer.moneyAmount + "§r§a and received " +
                    offer.requestedAmount + " " + offer.requestedItem.name());

            tradeRequests.remove(player.getUniqueId());

            // Update Scoreboards
            PersonalBalanceScoreboard senderscoreboard = new PersonalBalanceScoreboard(balanceManager);
            senderscoreboard.show(senderPlayer);
            PersonalBalanceScoreboard scoreboard = new PersonalBalanceScoreboard(balanceManager);
            scoreboard.show(player);

            return true;
        }

        // Trade offer
        if (args.length != 4) {
            player.sendMessage("§cUsage: /trade <player> <money> <item> <amount>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage("§cInvalid player.");
            return true;
        }
        if (target.equals(player)) {
            player.sendMessage("§cCannot trade with yourself!");
            return true;
        }

        double money;
        try {
            if (Objects.equals(args[1], "all_money")) money = balanceManager.getBalance(player.getUniqueId());
            else money = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid argument; invalid number set to money.");
            return true;
        }

        if (money <= 0 || money > 5_000_000) {
            player.sendMessage("§cInvalid amount, range §l$1 - $5,000,000!");
            return true;
        }

        Material item;
        try {
            item = Material.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cInvalid item type.");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cInvalid item amount.");
            return true;
        }

        if (amount > balanceManager.getBalance(player.getUniqueId())) {
            player.sendMessage("§cInvalid amount, you're too broke for that :(");
            return true;
        }

        // Create trade offer
        tradeRequests.put(target.getUniqueId(),
                new TradeOffer(player.getUniqueId(), money, item, amount));

        player.sendMessage("§aTrade offer sent to " + target.getName() + "!");
        target.sendMessage("§e" + player.getName() + " wants to trade $" + money + " for " +
                amount + " " + item.name() + ". Type §a/trade accept §eto accept.");

        return true;
    }

    private boolean hasItem(Player player, Material item, int amount) {
        int total = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack != null && stack.getType() == item) total += stack.getAmount();
        }
        return total >= amount;
    }

    private void removeItem(Player player, Material item, int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack == null) continue;
            if (stack.getType() == item) {
                if (stack.getAmount() <= remaining) {
                    remaining -= stack.getAmount();
                    player.getInventory().setItem(i, null);
                } else {
                    stack.setAmount(stack.getAmount() - remaining);
                    player.getInventory().setItem(i, stack);
                    break;
                }
            }
            if (remaining <= 0) break;
        }
    }

    // TradeOffer record
    private static class TradeOffer {
        UUID senderUUID;
        double moneyAmount;
        Material requestedItem;
        int requestedAmount;

        public TradeOffer(UUID senderUUID, double moneyAmount, Material requestedItem, int requestedAmount) {
            this.senderUUID = senderUUID;
            this.moneyAmount = moneyAmount;
            this.requestedItem = requestedItem;
            this.requestedAmount = requestedAmount;
        }
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
        else if (args.length == 2)
            return new ArrayList<>(Arrays.asList("1000", "2000", "all_money"));
        else if (args.length == 3)
            return new ArrayList<>(Arrays.asList("diamond", "grass_block", "item_id"));
        else if (args.length == 4)
            return new ArrayList<>(Arrays.asList("1", "64", "item_amount"));
        else
            return new ArrayList<>();
    }
}