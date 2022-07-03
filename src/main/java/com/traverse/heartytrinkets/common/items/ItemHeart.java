package com.traverse.heartytrinkets.common.items;

import com.traverse.heartytrinkets.common.util.HeartType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHeart extends BaseItem {

    protected final HeartType type;

    public ItemHeart(HeartType type) {
        super(0, 0.0F);
        this.type = type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            player.heal(this.type.healAmount);
            if (!player.isCreative()) player.eatFood(world, stack);
        }
        return stack;
    }
}
