package com.traverse.heartytrinkets.common.util;

import com.google.common.base.Preconditions;
import com.traverse.heartytrinkets.common.HeartyTrinkets;
import com.traverse.heartytrinkets.common.items.ItemCanisterBelt;
import com.traverse.heartytrinkets.mixin.PlayerEntityAccessor;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;

public class HealthModifier {

    public static final UUID HEALTH_MODIFIER_ID = UUID.fromString("caa44aa0-9e6e-4a57-9759-d2f64abfb7d3");

    public static void updatePlayerHealth(PlayerEntity player, ItemStack stack, boolean addHealth) {
        EntityAttributeInstance health = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        float diff = player.getMaxHealth() - player.getHealth();

        int [] hearts = new int[HeartType.values().length];

        if(addHealth && !stack.isEmpty()) {
            int [] beltHearts =((ItemCanisterBelt) stack.getItem()).getHeartCount(stack);
            Preconditions.checkArgument(beltHearts.length == HeartType.values().length, "Array must be same length as enum length");
            for (int i = 0; i < hearts.length; i++) {
                hearts[i] += beltHearts[i];
            }
        }

        int extraHearts = 0;
        for (int i = 0; i < hearts.length; i++) {
            extraHearts += MathHelper.clamp(hearts[i], 0, HeartyTrinkets.CONFIG.stackSize * 2);
        }

        EntityAttributeModifier modifier = health.getModifier(HEALTH_MODIFIER_ID);
        if(modifier != null) {
            if(modifier.getValue() == extraHearts) return;

            health.removeModifier(modifier);
        }

        health.addPersistentModifier(new EntityAttributeModifier(HEALTH_MODIFIER_ID, HeartyTrinkets.MOD_ID + ":extra_hearts", extraHearts, EntityAttributeModifier.Operation.ADDITION));
        float amount = MathHelper.clamp(player.getMaxHealth() - diff, 0.0f, player.getMaxHealth());
        if(amount > 0.0f) {
            player.setHealth(amount);
        } else {
            ((PlayerEntityAccessor)player).callCloseHandledScreen();
            player.kill();
        }
    }
}
