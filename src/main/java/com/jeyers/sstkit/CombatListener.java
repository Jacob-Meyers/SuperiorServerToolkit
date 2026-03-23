package com.jeyers.sstkit;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

///
/// Created by Jacob Meyers (TeamJEM)
/// File Created 3/21/2026
/// Last Edit    3/21/2026
///

public class CombatListener implements Listener {
    static final Map<UUID, Long> combatTagged = new HashMap<>();

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player victim) {
            long now = System.currentTimeMillis();
            combatTagged.put(victim.getUniqueId(), now);

            if (event instanceof EntityDamageByEntityEvent byEntity) {

                if (byEntity.getDamager() instanceof Player attacker) {
                    combatTagged.put(attacker.getUniqueId(), now);
                } else if (byEntity.getDamager() instanceof Projectile proj &&
                        proj.getShooter() instanceof Player shooter) {
                    combatTagged.put(shooter.getUniqueId(), now);
                }
            }
        }
    }
}
