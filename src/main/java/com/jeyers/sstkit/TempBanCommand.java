package com.jeyers.sstkit;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempBanCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public TempBanCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (label.equalsIgnoreCase("untempban") || label.equalsIgnoreCase("pardontempban")) {
            if (args.length < 1) {
                sender.sendMessage("§cUsage: /"+label+" <player>");
                return true;
            }
            if (!plugin.getConfig().contains("tempbans." + args[0])) {
                sender.sendMessage("§c" + args[0] + " has no temporary ban.");
                return true;
            }
            plugin.getConfig().set("tempbans." + args[0], null);
            sender.sendMessage("§aPlayer " + args[0] + "'s temporary ban has been pardoned.");
            plugin.saveConfig();
            return true;
        } else if (label.equalsIgnoreCase("tempbanlist")) {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("tempbans");

            StringBuilder tempbanlist = new StringBuilder();
            if (section != null) {
                for (String warp : section.getKeys(false)) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(warp);
                    if (target == null)
                        continue;
                    double timeTill = Double.parseDouble(
                            String.format("%.2f",
                                    (plugin.getConfig().getDouble("tempbans."
                                            + warp
                                            + "."
                                            + target.getUniqueId().toString()
                                            + ".time") - System.currentTimeMillis()) / (1000.0 * 60 * 60)));
                    if (timeTill <= 0)
                        continue;  /// DON'T WORRY ABOUT UNBANNING THAT WILL HAPPEN ON REJOIN!
                    tempbanlist.append("\n   ").append(warp)
                            .append("§a « Time: ")
                                .append(timeTill)
                                .append("hrs")
                            .append("§b « Reason: ")
                                .append(plugin.getConfig().getString("tempbans." + warp + "." + target.getUniqueId().toString() + ".reason"))
                            .append("§e « Banned By: ")
                                .append(plugin.getConfig().getString("tempbans." + warp + "." + target.getUniqueId().toString() + ".bannedby"));
                }
            }
            sender.sendMessage("Temporary Ban List: " + tempbanlist.toString());
            if (tempbanlist.isEmpty()) sender.sendMessage("   §cNo temporary bans found!");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§cUsage: /tempban <player> <duration in minutes> <reason>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found!");
            return true;
        }

        double durationHours;
        try {
            durationHours = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid duration!");
            return true;
        }

        String banReason = appendRangeWithDelimiter(args, 2, args.length, " ");


        double unbanTime = System.currentTimeMillis() + durationHours * 60 * 60 * 1000; // convert hours to ms
        String uuid = target.getUniqueId().toString();

        plugin.getConfig().set("tempbans." + target.getName() + "." + uuid + ".time", unbanTime);
        plugin.getConfig().set("tempbans." + target.getName() + "." + uuid + ".reason", banReason);
        plugin.getConfig().set("tempbans." + target.getName() + "." + uuid + ".bannedby", sender.getName());
        plugin.saveConfig();

        Player player = Bukkit.getPlayer(args[0]);
        if (player!=null) player.kick(Component.text("§cYou are temporarily banned for " + durationHours + " hours.\n\n§cReason: §b"+banReason+"\n§cBanned By: §b"+sender.getName()));
        sender.sendMessage("§aPlayer " + target.getName() + " has been temp banned for " + durationHours + " hours.");

        return true;
    }

    public static String appendRangeWithDelimiter(String[] array, int start, int end, String delimiter) {
        String[] range = Arrays.copyOfRange(array, start, end);
        return String.join(delimiter, range);
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        List<String> completions = new ArrayList<>();

        List<String> numbers = new ArrayList<>(List.of("12","24","48","72","168","720","ban_hours"));
        List<String> reason = new ArrayList<>(List.of("reason"));

        if ((alias.equals("pardontempban") || alias.equals("untempban")) && args.length <= 1) {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("tempbans");

            if (section != null) {
                for (String warp : section.getKeys(false)) {
                    if (warp.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(warp);
                    }
                }
            }
            return completions;
        } else if (args.length <= 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .toList();
        }
        if (alias.equals("tempban") && args.length == 2)
            return numbers;
        if (alias.equals("tempban") && args.length == 3)
            return reason;
        else
            return new ArrayList<>();
    }
}