package com.traverse.heartytrinkets.common.config;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = HeartyTrinkets.MOD_ID)
public class ConfigHandler implements ConfigData {

    @Comment("Canister Stack Size")
    public int stackSize = 10;

    @Comment("Amethyst Apple Hunger Value")
    public int hunger = 20;

    @Comment("Amethyst Apple Saturation Value")
    public float saturation =0.8f;

    @Comment("Wither Rib Drop Chance")
    public double witherRibDropChance = 0.15;

    @Comment("Allow Player Starting Tweaks")
    public boolean allowPlayerStartingTweaks = false;

    @Comment("Starting Health of Player (Default:20)" )
    public int startingHealth = 20;
}
