package com.sweattypalms.skyblock.core.items;

import com.sweattypalms.skyblock.core.items.builder.Rarity;
import com.sweattypalms.skyblock.core.items.builder.SimpleSkyblockItem;
import com.sweattypalms.skyblock.core.items.builder.SkyblockItem;
import com.sweattypalms.skyblock.core.items.builder.SkyblockItemType;
import com.sweattypalms.skyblock.core.items.builder.abilities.AbilityManager;
import com.sweattypalms.skyblock.core.player.Stats;
import org.bukkit.Material;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemManager {
    public static Map<String, SkyblockItem> ITEMS_LIST = new HashMap<>(Map.of(
    ));

    public static void initSimpleItems() {
        diamondSword();
        undeadSword();
        initReflection();
    }

    private static void initReflection() {
        Reflections reflections = new Reflections("com.sweattypalms.skyblock.core.items");
        Set<Class<? extends SkyblockItem>> itemClasses = reflections.getSubTypesOf(SkyblockItem.class);

        for (Class<? extends SkyblockItem> clazz : itemClasses) {
            try {
                if (clazz == SimpleSkyblockItem.class) continue;
                SkyblockItem item = clazz.getDeclaredConstructor().newInstance();
                ITEMS_LIST.put(item.getId(), item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void diamondSword(){
        SkyblockItem diamondSword = SimpleSkyblockItem.builder()
                .id("diamond_sword")
                .displayName("Diamond Sword")
                .material(Material.DIAMOND_SWORD)
                .stats(Map.of(Stats.DAMAGE, 35d))
                .baseRarity(Rarity.UNCOMMON)
                .itemType(SkyblockItemType.SWORD)
                .build();

    }
    private static void undeadSword(){
        SkyblockItem undeadSword = SimpleSkyblockItem.builder()
                .id("undead_sword")
                .displayName("Undead Sword")
                .material(Material.IRON_SWORD)
                .staticLore(List.of("$7Deals $a+100% $7damage to", "$7Skeletons, Zombies,", "$7Withers, and Zombie", "$7Pigwomen."))
                .stats(Map.of(Stats.DAMAGE, 30d, Stats.HEALTH, 50d))
                .baseRarity(Rarity.COMMON)
                .itemType(SkyblockItemType.SWORD)
                .abilities(List.of(AbilityManager.UNDEAD_SWORD_ABILITY))
                .build();
    }
}