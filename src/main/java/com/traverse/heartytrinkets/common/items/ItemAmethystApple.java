package com.traverse.heartytrinkets.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmethystApple extends BaseItem {

    public ItemAmethystApple(int hunger, float saturation) {
        super(hunger, saturation);
    }

    //20, 0.8f

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient && user instanceof PlayerEntity player) {
            player.eatFood(world,stack);
            applyAppleEffect(player);
        }

        return stack;
    }

    private void applyAppleEffect(LivingEntity player) {
        int duration = 2400;
        int amplifier = 0;
        boolean ambiance = false;
        boolean showParticles = false;
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, amplifier, ambiance, showParticles));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,  duration, amplifier, ambiance, showParticles));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, duration, amplifier, ambiance, showParticles));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,  duration, amplifier, ambiance, showParticles));
    }
}
