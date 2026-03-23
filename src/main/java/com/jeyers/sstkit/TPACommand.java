package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.*;

import static com.jeyers.sstkit.CombatListener.combatTagged;
import static com.jeyers.sstkit.SupToolkit.COMBAT_TIME_TPA;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/20/2026
/// Last Edit    3/21/2026
///

public class TPACommand implements CommandExecutor, TabCompleter {

    static Map<String, Player> tpaCommandCache = new HashMap<>();

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        if (args.length < 1 && !label.equalsIgnoreCase("tpacancel"))
            return true;

        Long lastCombat = combatTagged.get(player.getUniqueId());
        if (lastCombat != null && (System.currentTimeMillis() - lastCombat) < COMBAT_TIME_TPA) {
            player.sendMessage("§cYou cannot run command /" + label + " while in combat!");
            long remainingSeconds = Math.max(0, (COMBAT_TIME_TPA - (System.currentTimeMillis() - lastCombat)) / 1000);
            player.sendMessage("§bYou are detected in combat for another §c" + remainingSeconds + "§b seconds!");
            return true;
        }

        ConsoleCommandSender console = Bukkit.getConsoleSender();
        if (label.equalsIgnoreCase("tpa")) {
            String senderName = player.getName();
            if (tpaCommandCache.containsKey(senderName)) {
                player.sendMessage("§cAlready sent a /tpa request to a player, use /tpacancel to cancel the request.");
                return true;
            }
            if (senderName.equalsIgnoreCase(args[0])) {
                player.sendMessage("§cCannot send a /tpa request to yourself.");
                return true;
            }
            Player targetPlayer = Bukkit.getPlayerExact(args[0].toLowerCase());
            if (targetPlayer == null){
                player.sendMessage("§cPlayer not found!");
                return true;
            }

            player.sendMessage("§bSent a /tpa request to a §e"+targetPlayer.getName()+"§b, use /tpacancel if you want to cancel the request.");
            tpaCommandCache.put(senderName, targetPlayer);
            console.sendMessage(senderName+" sent a /tpa to "+targetPlayer.getName());
            targetPlayer.sendMessage("§e" + senderName+ " is requesting to tp to you ; Use §b'/tpyes " + senderName + "'§e to accept or §b'/tpno " + senderName + "'§e to decline");
        } else if (label.equalsIgnoreCase("tpacancel")) {
            if (tpaCommandCache.containsKey(player.getName())){
                player.sendMessage("§eCanceled your /tpa request.");
                tpaCommandCache.get(player.getName()).sendMessage("§e"+player.getName() + "§c canceled their /tpa request to you");
                tpaCommandCache.remove(player.getName());
            } else {
                player.sendMessage("§cYou have no active /tpa request.");
            }
            return true;
        } else {
            String tpaSenderUser = args[0];
            if (tpaCommandCache.containsKey(tpaSenderUser)){
                if (tpaCommandCache.get(tpaSenderUser).equals(player)){
                    String receivingPlayerName = player.getName();
                    Player senderPlayer = Bukkit.getPlayerExact(tpaSenderUser);
                    if (label.equalsIgnoreCase("tpyes") && senderPlayer != null){
                        player.sendMessage("§cAccepted /tpa request from §e"+tpaSenderUser);
                        console.sendMessage(receivingPlayerName+" accepted /tpa request from "+tpaSenderUser);
                        senderPlayer.teleport(player);
                    } else if (senderPlayer != null) {
                        player.sendMessage("§cDeclined /tpa request from §e"+tpaSenderUser);
                        console.sendMessage(receivingPlayerName+" declined /tpa request from "+tpaSenderUser);
                        senderPlayer.sendMessage("§e"+receivingPlayerName + "§c declined your /tpa request");
                    }
                    tpaCommandCache.remove(tpaSenderUser);
                    return true;
                }
            } else {
                player.sendMessage("§cThere is no active /tpa request coming from §e"+tpaSenderUser);
            }
        }

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
