package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;
import java.net.URI;
import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Objects;

///
/// CODE BASED OFF OF SOME RANDOM GUY ON THE INTERNET!!!
/// I LOST THE ORIGINAL LINK, THIS IS NOT COMPLETELY MY WORK.
/// I ONLY IMPLEMENTED IT INTO PAPER/BUKKIT.
///

public class VPNCheckCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public VPNCheckCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cInvalid argument; Usage: /<command> <player>");
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player==null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        String API_KEY = plugin.getConfig().getString("VPNCHECK-iphubInfo-API_KEY");
        if (Objects.equals(API_KEY, "API_KEY_HERE")) {
            sender.sendMessage("§cSet the VPNCHECK-iphubInfo-API_KEY in the config.");
            return true;
        }

        String ip = Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URI uri = URI.create("https://v2.api.iphub.info/ip/" + ip);
                URL url = uri.toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-Key", API_KEY);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close();

                JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
                int block = json.get("block").getAsInt();

                if (block == 1 || block == 2) {
                    sender.sendMessage("§cVPN/Proxy detected from IP: " + ip);
                } else {
                    sender.sendMessage("§aNo VPN detected from IP: " + ip);
                }

            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage("§cFailed to check VPN status.");
            }
        });

        return true;
    }
}