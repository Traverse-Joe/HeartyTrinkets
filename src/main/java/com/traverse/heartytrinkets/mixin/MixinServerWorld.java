package com.traverse.heartytrinkets.mixin;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class MixinServerWorld {

    @Inject(method = "addPlayer", at = @At(value = "HEAD"))
    private void addPlayer(ServerPlayerEntity player, CallbackInfo ci) {
        World world = player.getWorld();
        if(!world.isClient()) {
            adjustHealth(player);
        }
    }

    @Inject(method = "tickEntity", at = @At(value = "HEAD"))
    private void tickPlayer(Entity entity, CallbackInfo ci) {
        World world = entity.getWorld();
        if(!world.isClient()  && entity instanceof PlayerEntity player) {
            adjustHealth(player);
        }
    }

    private static void adjustHealth(PlayerEntity player) {
        if(HeartyTrinkets.CONFIG.allowPlayerStartingTweaks && HeartyTrinkets.CONFIG.startingHealth > 0) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(HeartyTrinkets.CONFIG.startingHealth);
        } else {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20);
        }
    }

}
