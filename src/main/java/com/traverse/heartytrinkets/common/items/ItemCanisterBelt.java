package com.traverse.heartytrinkets.common.items;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import com.traverse.heartytrinkets.common.registry.ModItems;
import com.traverse.heartytrinkets.common.util.HealthModifier;
import com.traverse.heartytrinkets.common.util.HeartType;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.emi.trinkets.api.TrinketItem.equipItem;

public class ItemCanisterBelt extends BaseItem implements NamedScreenHandlerFactory, Trinket {

    public ItemCanisterBelt() {
        super(1);
        TrinketsApi.registerTrinket(this, this);
    }

    //ToDo: actually check heart amulet container amount rather than use it as a string

    public int[] getHeartCount(ItemStack stack) {
        if (stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt.contains(CanisterBeltContainer.HEART_AMOUNT))
                return nbt.getIntArray(CanisterBeltContainer.HEART_AMOUNT);
        }
        return new int[HeartType.values().length];
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final ItemStack stack = user.getMainHandStack();
        if (!user.isSneaking() && equipItem(user, stack)) {
            return TypedActionResult.success(stack, world.isClient());
        }
        if (!world.isClient() && user.isSneaking()) {
            user.openHandledScreen(this);
            //ContainerProviderRegistry.INSTANCE.openContainer(new Identifier(HeartyTrinkets.MOD_ID,  "belt_canister"), user, packetByteBuf -> packetByteBuf.writeItemStack(stack));
        }
        return TypedActionResult.pass(stack);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(Util.createTranslationKey("tooltip", new Identifier(HeartyTrinkets.MOD_ID, "canisterbelt")), MinecraftClient.getInstance().options.sneakKey.getBoundKeyLocalizedText()).setStyle(Style.EMPTY.withFormatting(Formatting.GOLD)));
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        HealthModifier.updatePlayerHealth((PlayerEntity) entity, stack, true);
        Trinket.super.onEquip(stack, slot, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        HealthModifier.updatePlayerHealth((PlayerEntity) entity, ItemStack.EMPTY, false);
        Trinket.super.onUnequip(stack, slot, entity);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(this.getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        Hand hand = getHandForCanisterBelt(player);
        return new CanisterBeltContainer(syncId, player, hand != null ? player.getStackInHand(hand) : ItemStack.EMPTY);
    }

    public static Hand getHandForCanisterBelt(PlayerEntity player) {
        if (player.getMainHandStack().getItem() == ModItems.CANISTER_BELT) {
            return Hand.MAIN_HAND;
        } else if (player.getOffHandStack().getItem() == ModItems.CANISTER_BELT) {
            return Hand.OFF_HAND;
        }

        return null;
    }
}
