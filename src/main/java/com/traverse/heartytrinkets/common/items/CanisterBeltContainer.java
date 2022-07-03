package com.traverse.heartytrinkets.common.items;

import com.traverse.heartytrinkets.common.HeartyTrinkets;
import com.traverse.heartytrinkets.common.util.ItemInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;

public class CanisterBeltContainer extends ScreenHandler {

    public PlayerInventory playerInventory;
    public ItemInventory inventory;
    public static final String HEART_AMOUNT = "heart_amount";

    public CanisterBeltContainer(int syncId, PlayerEntity player, ItemStack stack) {
        super(HeartyTrinkets.CANISTER_BELT_CONTAINER, syncId);
        this.playerInventory = player.getInventory();
        this.inventory = ItemInventory.createVirtualInventory(stack);

        //Canister Inventory
        this.addSlot(new HeartSlot(this.inventory, 0, 44, 33)); //RED
        this.addSlot(new HeartSlot(this.inventory, 1, 68, 33)); //ORANGE
        this.addSlot(new HeartSlot(this.inventory, 2, 92, 33)); //GREEN
        this.addSlot(new HeartSlot(this.inventory, 3, 116, 33)); //BLUE

        //Add player inventory slots
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 56 + 86;
            if (row == getSlotFor(playerInventory, stack)) {
                addSlot(new LockedSlot(playerInventory, row, x, y));
                continue;
            }

            addSlot(new Slot(playerInventory, row, x, y));
        }

        for (int row = 1; row < 4; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + (56 + 10);
                addSlot(new Slot(playerInventory, col + row * 9, x, y));
            }
        }
    }

    @Override
    public void close(PlayerEntity player) {
        Hand hand = ItemCanisterBelt.getHandForCanisterBelt(player);
        if(hand != null) {
            NbtCompound compound = inventory.serialize();
            player.getStackInHand(hand).getOrCreateNbt().put("itemInventory", compound);
        }
        NbtCompound nbt = player.getStackInHand(hand).getNbt();
        int[] hearts = new int[this.inventory.size()];
        for (int i = 0; i < hearts.length; i++) {
            ItemStack stack = this.inventory.getStack(i);
            if (!stack.isEmpty()) hearts[i] = stack.getCount() * 2;
        }
        nbt.putIntArray(HEART_AMOUNT, hearts);
        player.getStackInHand(hand).setNbt(nbt);

        super.close(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        final Slot slot  = this.slots.get(index);
        if(slot != null && slot.hasStack()){
            final ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if(index < inventory.size()){
                if(!this.insertItem(slotStack, inventory.size(), this.slots.size(), true)){
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.insertItem(slotStack, 0, inventory.size(), false)){
                return ItemStack.EMPTY;
            }
            if(slotStack.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();

        }
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    public static class HeartSlot extends Slot {

        public HeartSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount() {
            return HeartyTrinkets.CONFIG.stackSize;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return super.canInsert(stack) && stack.getItem() instanceof BaseCanister && ((BaseCanister) stack.getItem()).type.ordinal() == this.getIndex();
        }
    }

    public static class LockedSlot extends Slot {

        public LockedSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }
    }

    public int getSlotFor(Inventory inventory, ItemStack stack) {
        for(int i = 0; i < inventory.size(); ++i) {
            if(!inventory.getStack(i).isEmpty() && stackEqualExact(stack, inventory.getStack(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && ItemStack.areNbtEqual(stack1, stack2);
    }
}
