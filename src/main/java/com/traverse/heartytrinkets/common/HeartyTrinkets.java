package com.traverse.heartytrinkets.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.traverse.heartytrinkets.common.config.ConfigHandler;
import com.traverse.heartytrinkets.common.config.HeartyTrinketsConfig;
import com.traverse.heartytrinkets.common.items.CanisterBeltContainer;
import com.traverse.heartytrinkets.common.registry.ModItems;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HeartyTrinkets implements ModInitializer {

    public static final String MOD_ID = "heartytrinkets";
    public static ItemGroup TAB = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(ModItems.CANISTER_BELT));
    public static ConfigHandler CONFIG = new ConfigHandler();
    public static HeartyTrinketsConfig HTConfig;
    public static ScreenHandlerType<CanisterBeltContainer> CANISTER_BELT_CONTAINER;

    @Override
    public void onInitialize() {
        AutoConfig.register(ConfigHandler.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ConfigHandler.class).getConfig();
        jsonSetup();
        ModItems.registerItems(Registry.ITEM);
        CANISTER_BELT_CONTAINER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "canister_belt"), ((syncId, inventory) -> new CanisterBeltContainer(syncId, inventory.player, inventory.player.getMainHandStack())));
    }

    private void jsonSetup() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File folder = FabricLoader.getInstance().getConfigDir().resolve("heartytrinkets").toFile();
        folder.mkdir();
        File file = folder.toPath().resolve("drops.json").toFile();
        try {
            if (file.exists()) {
                HTConfig = gson.fromJson(new FileReader(file), HeartyTrinketsConfig.class);
                return;
            }
            HTConfig = new HeartyTrinketsConfig();
            HTConfig.addEntrytoMap("red", "hostile", 0.05);
            HTConfig.addEntrytoMap("orange", "boss", 1.0);
            HTConfig.addEntrytoMap("green", "dragon", 1.0);
            HTConfig.addEntrytoMap("blue", "minecraft:warden", 1.0);
            String json = gson.toJson(HTConfig, HeartyTrinketsConfig.class);
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
