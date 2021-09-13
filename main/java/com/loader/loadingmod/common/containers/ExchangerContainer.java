package com.loader.loadingmod.common.containers;

import com.loader.loadingmod.core.init.BlockInit;
import com.loader.loadingmod.core.init.ContainerTypeInit;
import com.loader.loadingmod.common.tileentities.ExchangerTileEntity;
import com.loader.loadingmod.common.tileentities.TableTileEntity;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.loader.loadingmod.client.util.FunctionalIntReferenceHolder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.SlotItemHandler;

public class ExchangerContainer extends Container{
	public ExchangerTileEntity tileEntity;
	private IWorldPosCallable canInteractWithCallable;
	public FunctionalIntReferenceHolder currentSmeltTime;

	// Server Constructor
	public ExchangerContainer(int windowID, PlayerInventory playerInv, ExchangerTileEntity tile) {
		super(ContainerTypeInit.EXCHANGER.get(), windowID);

		this.tileEntity = tile;
		this.canInteractWithCallable = IWorldPosCallable.create(tile.getLevel(), tile.getBlockPos());

		final int slotSizePlus2 = 18;
		final int startX = 8;
		
		// Furnace Slots
		this.addSlot(new SlotItemHandler(tile.getInventory(), 0, 56, 34));
		this.addSlot(new SlotItemHandler(tile.getInventory(), 1, 116, 34));

		this.addDataSlot(currentSmeltTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentSmeltTime,
				value -> this.tileEntity.currentSmeltTime = value));
		
		// Main Player Inventory
		final int startY = 84;

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				this.addSlot(new Slot(playerInv, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}
		
		// Hotbar
		int hotbarY = 142;
		for (int column = 0; column < 9; column++) {
			this.addSlot(new Slot(playerInv, column, startX + (column * slotSizePlus2), hotbarY));
		}

	}

	// Client Constructor
	public ExchangerContainer(final int windowID,final PlayerInventory playerInv,final PacketBuffer data) {
		this(windowID, playerInv, getTileEntity(playerInv, data));
	}

	private static ExchangerTileEntity getTileEntity(final PlayerInventory playerInv,final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "playerInv cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof ExchangerTileEntity) {
			return (ExchangerTileEntity) tileAtPos;
		}
		throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, BlockInit.exchanger_block.get());
	}

	@Nonnull
	@Override
	public ItemStack quickMoveStack(PlayerEntity player, int index) {
		
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			returnStack = slotStack.copy();

			final int containerSlots = this.slots.size() - player.inventory.items.size();
			if (index < containerSlots) {
				if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			
			if (slotStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			//
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return returnStack;
	}

	@OnlyIn(Dist.CLIENT)
	public int getSmeltProgressionScaled() {
		return this.currentSmeltTime.get() != 0 && this.tileEntity.maxSmeltTime != 0
				? this.currentSmeltTime.get() * 24 / this.tileEntity.maxSmeltTime
				: 0;
	}
}
