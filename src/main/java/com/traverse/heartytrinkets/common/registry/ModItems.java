package com.traverse.heartytrinkets.common.registry;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import com.traverse.heartytrinkets.common.config.ConfigHandler;
import com.traverse.heartytrinkets.common.items.*;
import com.traverse.heartytrinkets.common.util.HeartType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static Item RED_HEART = new ItemHeart(HeartType.RED);
    public static Item ORANGE_HEART = new ItemHeart(HeartType.ORANGE);
    public static Item GREEN_HEART = new ItemHeart(HeartType.GREEN);
    public static Item BLUE_HEART = new ItemHeart(HeartType.BLUE);
    public static Item CANISTER = new BaseItem();
    public static Item CANISTER_BELT = new ItemCanisterBelt();
    public static Item RED_HEART_CRYSTAL = new BaseItem();
    public static Item ORANGE_HEART_CRYSTAL = new BaseItem();
    public static Item GREEN_HEART_CRYSTAL = new BaseItem();
    public static Item BLUE_HEART_CRYSTAL = new BaseItem();
    public static Item WITHER_RIB = new BaseItem();
    public static Item RED_HEART_CANISTER;
    public static Item ORANGE_HEART_CANISTER;
    public static Item GREEN_HEART_CANISTER;
    public static Item BLUE_HEART_CANISTER ;
    public static Item AMETHYST_APPLE;

    public static void registerItems(Registry<Item> registry) {
        RED_HEART_CANISTER = new  BaseCanister(HeartyTrinkets.CONFIG.stackSize, HeartType.RED);
        ORANGE_HEART_CANISTER = new BaseCanister(HeartyTrinkets.CONFIG.stackSize, HeartType.ORANGE);
        GREEN_HEART_CANISTER = new BaseCanister(HeartyTrinkets.CONFIG.stackSize,HeartType.GREEN);
        BLUE_HEART_CANISTER = new BaseCanister(HeartyTrinkets.CONFIG.stackSize,HeartType.BLUE);
        AMETHYST_APPLE = new ItemAmethystApple(HeartyTrinkets.CONFIG.hunger, HeartyTrinkets.CONFIG.saturation);

        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "red_heart"), RED_HEART);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "orange_heart"), ORANGE_HEART);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "green_heart"), GREEN_HEART);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "blue_heart"), BLUE_HEART);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "canister"), CANISTER);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "canister_belt"), CANISTER_BELT);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "red_heart_crystal"), RED_HEART_CRYSTAL);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "orange_heart_crystal"), ORANGE_HEART_CRYSTAL);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "green_heart_crystal"), GREEN_HEART_CRYSTAL);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "blue_heart_crystal"), BLUE_HEART_CRYSTAL);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "wither_rib"), WITHER_RIB);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "red_heart_canister"), RED_HEART_CANISTER);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "orange_heart_canister"), ORANGE_HEART_CANISTER);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "green_heart_canister"), GREEN_HEART_CANISTER);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "blue_heart_canister"), BLUE_HEART_CANISTER);
        registry.register(registry, new Identifier(HeartyTrinkets.MOD_ID, "amethyst_apple"), AMETHYST_APPLE);
    }

}
