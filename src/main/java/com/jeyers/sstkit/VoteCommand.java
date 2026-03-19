package com.jeyers.sstkit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class VoteCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public VoteCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        String voteLink = plugin.getConfig().getString("voteLink");
        assert voteLink != null;
        Component component = Component.text("[ !!! «= Click to Vote =» !!! ]")
                .color(NamedTextColor.GREEN)
                .style(Style.style(TextDecoration.BOLD))
                .hoverEvent(HoverEvent.showText(Component.text("Click to Vote!")))
                .clickEvent(ClickEvent.openUrl(voteLink));
        sender.sendMessage(component);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command,
                                      @NonNull String alias, String @NonNull [] args) {
        return new ArrayList<>();
    }
}