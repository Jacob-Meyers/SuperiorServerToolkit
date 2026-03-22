package com.jeyers.sstkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.lang.reflect.Method;
import java.util.logging.Level;

import static com.jeyers.sstkit.SupToolkit.scoreboardEnabled;
import static com.jeyers.sstkit.SupToolkit.serverNameScoreboard;

public class PersonalBalanceScoreboard {

    private final BalanceManager balanceManager;

    public PersonalBalanceScoreboard(BalanceManager balanceManager) {
        this.balanceManager = balanceManager;
    }

    @SuppressWarnings("deprecation")
    public void show(Player player) {
        if (!scoreboardEnabled) return;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("balance", "dummy", "§b" + serverNameScoreboard);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        double bal = balanceManager.getBalance(player.getUniqueId());
        String formatted = BalanceManager.formatMoney(bal);

        String displayText = "§fBalance: §a$" + formatted;
        objective.getScore("§r" + displayText).setScore(1);

        try {
            Class<?> numberFormatClass = Class.forName("io.papermc.paper.scoreboard.numbers.NumberFormat");
            Method blankMethod = numberFormatClass.getMethod("blank");
            Object blankFormat = blankMethod.invoke(null);

            Method setNumberFormatMethod = Objective.class.getMethod("numberFormat", numberFormatClass);
            //noinspection JavaReflectionInvocation
            setNumberFormatMethod.invoke(objective, blankFormat);

        } catch (ClassNotFoundException | NoSuchMethodException ignored) {
        } catch (Exception ex) {
            //noinspection UnstableApiUsage
            Bukkit.getLogger().log(Level.WARNING, "Failed to hide scoreboard numbers via Paper API", ex);
        }

        player.setScoreboard(board);
    }
}