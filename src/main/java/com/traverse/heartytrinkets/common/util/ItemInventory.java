package com.traverse.heartytrinkets.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class ItemInventory implements Inventory {
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public NbtCompound serialize() {
        return Inventories.writeNbt(new NbtCompound(), this.inventory);
    }

    public static ItemInventory deserialize(NbtCompound nbt) {
        ItemInventory inventory = new ItemInventory();
        if (nbt != null) {
            Inventories.readNbt(nbt, inventory.inventory);
        }
        return inventory;
    }

    public static ItemInventory createVirtualInventory(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        return deserialize(nbt.getCompound("itemInventory"));
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
