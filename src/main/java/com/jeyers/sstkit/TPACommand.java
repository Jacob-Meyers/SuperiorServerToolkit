package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.*;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/20/2026
/// Last Edit    3/20/2026
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

        if (label.equalsIgnoreCase("tpa")) {
            String senderName = player.getName();
            if (tpaCommandCache.containsKey(senderName)) {
                sender.sendMessage("§cAlready sent a /tpa request to a player, use /tpacancel to cancel the request.");
                return true;
            }
            if (senderName.equalsIgnoreCase(args[0])) {
                sender.sendMessage("§cCannot send a /tpa request to yourself.");
                return true;
            }
            Player targetPlayer = Bukkit.getPlayerExact(args[0].toLowerCase());
            if (targetPlayer == null){
                sender.sendMessage("§cPlayer does not exist or is offline.");
                return true;
            }

            tpaCommandCache.put(senderName, targetPlayer);
            targetPlayer.sendMessage("§e" + senderName+ " is requesting to tp to you ; Use §b'/tpaccept " + senderName + "'§e to accept or §b'/tpdecline " + senderName + "'§e to decline");
        } else if (label.equalsIgnoreCase("tpacancel")) {
            if (tpaCommandCache.containsKey(player.getName())){
                sender.sendMessage("§eCanceled your /tpa request.");
                tpaCommandCache.remove(player.getName());
            } else {
                sender.sendMessage("§cYou have no active /tpa request.");
            }
            return true;
        } else {
            String tpaSenderUser = args[0];
            if (tpaCommandCache.containsKey(tpaSenderUser)){
                if (tpaCommandCache.get(tpaSenderUser).equals(player)){
                    Player senderPlayer = Bukkit.getPlayerExact(tpaSenderUser);
                    if (label.equalsIgnoreCase("tpaccept") && senderPlayer != null)
                        senderPlayer.teleport(player);
                    tpaCommandCache.remove(tpaSenderUser);
                    return true;
                }
            } else {
                sender.sendMessage("§cThere is no active /tpa request coming from §e"+tpaSenderUser);
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        List<String> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .toList();

        if (args.length == 1)
            return onlinePlayers;
        else
            return new ArrayList<>();
    }
}
