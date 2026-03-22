package com.jeyers.sstkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BalanceManager {

    private final SupToolkit plugin;

    // Thread-safe cache
    private final Map<UUID, Double> balances = new ConcurrentHashMap<>();

    public BalanceManager(SupToolkit plugin) {
        this.plugin = plugin;
    }

    public double getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0.0);
    }

    public void setBalance(UUID uuid, double amount) {
        if (amount < 0) amount = 0;
        balances.put(uuid, amount);
    }

    public boolean has(UUID uuid, double amount) {
        if (amount < 0) return false;
        return getBalance(uuid) >= amount;
    }

    public void addBalance(UUID uuid, double amount) {
        balances.put(uuid, getBalance(uuid) + amount);
        if (getBalance(uuid) < 0)
            setBalance(uuid, 0);
    }

    public boolean removeBalance(UUID uuid, double amount) {
        if (amount <= 0) return false;

        double current = getBalance(uuid);
        if (current < amount) return false;

        balances.put(uuid, current - amount);
        return true;
    }

    public synchronized void transfer(UUID from, UUID to, double amount) {
        if (from.equals(to)) return;
        if (amount <= 0) return;

        double fromBalance = getBalance(from);

        if (fromBalance < amount) {
            return;
        }

        // Perform atomic update
        balances.put(from, fromBalance - amount);
        balances.put(to, getBalance(to) + amount);

    }

    public void loadBalance(UUID uuid, double amount) { balances.put(uuid, Math.max(0, amount)); }

    public void unload(UUID uuid) {
        balances.remove(uuid);
    }

    public Map<UUID, Double> getAllBalances() {
        return balances;
    }

    public void saveAll() {
        for (Map.Entry<UUID, Double> entry : balances.entrySet()) {
            saveToStorage(entry.getKey(), entry.getValue());
        }
    }

    public void save(UUID uuid) {
        saveToStorage(uuid, getBalance(uuid));
    }


    private void saveToStorage(UUID uuid, double balance) {
        plugin.getBalancesConfig().set("balances." + uuid.toString(), balance);
    }

    public double loadFromStorage(UUID uuid) {
        return plugin.getBalancesConfig().getDouble("balances." + uuid.toString(), plugin.getConfig().getDouble("defaultBalance"));
    }

    public static String formatMoney(double amount) {
        if (amount >= 1_000_000_000_000L) {
            return String.format("%.2fT", amount / 1_000_000_000_000L);
        } else if (amount >= 1_000_000_000) {
            return String.format("%.2fB", amount / 1_000_000_000);
        } else if (amount >= 1_000_000) {
            return String.format("%.2fM", amount / 1_000_000);
        } else {
            return String.format("%.0f", amount);
        }
    }
}