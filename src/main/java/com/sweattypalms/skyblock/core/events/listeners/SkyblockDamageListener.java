package com.sweattypalms.skyblock.core.events.listeners;

import com.sweattypalms.skyblock.SkyBlock;
import com.sweattypalms.skyblock.core.events.SkyblockPlayerDamageEntityEvent;
import com.sweattypalms.skyblock.core.helpers.DamageCalculator;
import com.sweattypalms.skyblock.core.items.builder.SkyblockItem;
import com.sweattypalms.skyblock.core.items.builder.abilities.IHasAbility;
import com.sweattypalms.skyblock.core.items.builder.abilities.types.DamageAbility;
import com.sweattypalms.skyblock.core.items.builder.abilities.types.FullSetBonus;
import com.sweattypalms.skyblock.core.items.builder.abilities.types.ITriggerable;
import com.sweattypalms.skyblock.core.mobs.SkyblockMob;
import com.sweattypalms.skyblock.core.player.Stats;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SkyblockDamageListener implements Listener {
    /**
     * This event should only be used for damage calculation.
     * CALLED FASTEST
     *
     * @param event SkyblockPlayerDamageEntityEvent
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSkyblockPlayerDamageEntityDamageCalculation(SkyblockPlayerDamageEntityEvent event) {
        // TODO: check for damage type : event.getDamageType()

        // Item Abilities (Item in hand)
        SkyblockItem item = event.getSkyblockPlayer().getSkyblockItemInHand();
        if (item instanceof IHasAbility iHasAbility) {
            iHasAbility.getAbilities().forEach(ability -> {
                if (!(ability instanceof ITriggerable triggerable)) return;
                boolean isDamageAbility = ability instanceof DamageAbility;
                isDamageAbility = isDamageAbility && ((DamageAbility) ability).preCalc();

                boolean shouldTrigger = triggerable.trigger(event) && isDamageAbility;
                if (shouldTrigger) {
                    ability.apply(event);
                }
            });
        }
        // Full Set Bonus
        FullSetBonus fullSetBonus = event.getSkyblockPlayer().getEquippedFullSetBonus();
        if (fullSetBonus != null) {
            // Not checking for trigger because it is already checked in getEquippedFullSetBonus() through isFullSetEquipped()
            fullSetBonus.apply(event);
        }

        double damage = DamageCalculator.calculateNormalDamage(event);
        event.setDamage(damage);
//        System.out.printf(
//                "Player %s damaged %s for %s damage%n",
//                event.getPlayer().getName(),
//                event.getEntity().getType(),
//                event.getDamage()
//                );
    }


    @EventHandler
    public void onSkyblockPlayerDamageEntity(SkyblockPlayerDamageEntityEvent event) {
        if (event.isCancelled()) return;


        SkyblockMob skyblockMob = event.getSkyblockMob();
        double damage = event.getDamage();
//        System.out.printf(
//                "Player %s damaged %s for %s damage%n",
//                event.getPlayer().getName(),
//                event.getEntity().getType(),
//                event.getDamage()
//        );

        // Item Abilities
        SkyblockItem item = event.getSkyblockPlayer().getSkyblockItemInHand();
        if (item instanceof IHasAbility iHasAbility) {
            iHasAbility.getAbilities().forEach(ability -> {
                if (!(ability instanceof ITriggerable triggerable)) return;
                boolean isDamageAbility = ability instanceof DamageAbility;
                isDamageAbility = isDamageAbility && !((DamageAbility) ability).preCalc();

                boolean shouldTrigger = triggerable.trigger(event) && isDamageAbility;
                if (shouldTrigger) {
                    ability.apply(event);
                }
            });
        }
        skyblockMob.damageEntityWithCause(damage, event.getDamageType(), event.getSkyblockPlayer());

        /* ------- FEROCITY ------- */

        // Implement extra logic ;; check for various damage types and apply ferocity / not.
        int ferocity = event.getSkyblockPlayer().getMaxStats().get(Stats.FEROCITY).intValue();

        int guaranteedHits = ferocity / 100;
        boolean extraHit = event.getSkyblockPlayer().getRandom().nextInt(100) < (ferocity % 100);

        Location soundLocation = event.getSkyblockPlayer().getPlayer().getLocation();
        assert soundLocation.getWorld() != null;

        for (int i = 0; i < guaranteedHits + (extraHit ? 1 : 0); i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (skyblockMob.getEntityInstance() == null) return;
                    if (skyblockMob.getEntityInstance().isDead() || skyblockMob.getEntityInstance().getHealth() <= 0)
                        return;
                    skyblockMob.damageEntityWithCause(damage, event.getDamageType(), event.getSkyblockPlayer());

                    /* ------- SOUND ------- */

                    soundLocation.getWorld().playSound(soundLocation, Sound.ITEM_FLINTANDSTEEL_USE, 1.5f, 0);
                    soundLocation.getWorld().playSound(soundLocation, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.2f, 2f);

                    /* ------- SOUND ------- */
                }
            }.runTaskLater(SkyBlock.getInstance(), 5L * i + 5);  // Assume 5 ticks (1/4 second) between hits
        }

        /* ------- FEROCITY ------- */


    }


}