package com.traverse.heartytrinkets.mixin;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import com.traverse.heartytrinkets.common.registry.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(method = "drop", at = @At("RETURN"))
    public void onDropped(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if(entity.world.isClient() || entity instanceof PlayerEntity) return;
        if(entity instanceof WitherSkeletonEntity && entity.world.random.nextDouble() <= HeartyTrinkets.CONFIG.witherRibDropChance) {
            entity.dropStack(new ItemStack(ModItems.WITHER_RIB, 1));
        }
        for (ItemStack stack : getEntityDrops(entity)){
            entity.dropStack(stack, 0);
        }
    }

    private static List<ItemStack> getEntityDrops(LivingEntity entity) {
        List<ItemStack> list = new ArrayList<>();
        handleEntry("red", entity, list);
        handleEntry("orange", entity, list);
        handleEntry("green", entity, list);
        handleEntry("blue", entity, list);
        return list;
    }

    private static void handleEntry(String category, LivingEntity entity, List<ItemStack> items) {
        for (Map.Entry <String, Double> entry : HeartyTrinkets.HTConfig.getHeartTypeEntries(category).entrySet()) {
            ItemStack stack = ItemStack.EMPTY;
            switch (category) {
                case "red":
                    stack = new ItemStack(ModItems.RED_HEART);
                    break;
                case "orange":
                    stack = new ItemStack(ModItems.ORANGE_HEART);
                    break;
                case "green":
                    stack = new ItemStack(ModItems.GREEN_HEART);
                    break;
                case "blue":
                    stack = new ItemStack(ModItems.BLUE_HEART);
                    break;
            }

            if(entry.getKey().equals(((EntityAccessor)entity).callGetSavedEntityId())) {
                addWithPercent(items, stack, entry.getValue());
            } else {
                switch (entry.getKey()) {
                    case "hostile":
                        if(entity instanceof Monster && entity.canUsePortals()) {
                            addWithPercent(items,stack, entry.getValue());
                        }
                        break;
                    case "boss":
                        if(!entity.canUsePortals() && !(entity instanceof EnderDragonEntity)) {
                            addWithPercent(items, stack, entry.getValue());
                        }
                        break;
                    case "dragon":
                        if(entity instanceof EnderDragonEntity) {
                            addWithPercent(items,stack, entry.getValue());
                        }
                        break;
                }
            }
        }
    }

    private static void addWithPercent(List<ItemStack> list, ItemStack stack, double percentage) {
        Random random = new Random();
        int percent = (int) (percentage * 100);
        if(random.nextInt(100) < percent) {
            list.add(stack);
        }
    }
}
