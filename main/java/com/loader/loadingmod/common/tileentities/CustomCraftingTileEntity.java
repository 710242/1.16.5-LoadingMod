package com.loader.loadingmod.common.tileentities;

import com.loader.loadingmod.common.containers.CustomCraftingContainer;
import com.loader.loadingmod.core.init.TileEntityTypesInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CustomCraftingTileEntity extends LockableLootTileEntity{
	private final int rows = 7;
	private final int size = 4;
	private final int invSize = size * rows + 10;
	private NonNullList<ItemStack> inventory = NonNullList.withSize(invSize, ItemStack.EMPTY);
	private int numPlayersUsing;

	public CustomCraftingTileEntity() {
		
		super(TileEntityTypesInit.custom_crafting_block.get());
		
	}

	@Override
	public int getContainerSize() {
		return invSize;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.inventory;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		
		this.inventory = itemsIn;
		
	}

	@Override
	protected ITextComponent getDefaultName() {
		
		return new TranslationTextComponent("container.crafting_station");
		
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		
		return new CustomCraftingContainer(id, player, this);
		
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
		
		super.save(compound);
		
		if (!this.trySaveLootTable(compound)) {
			
			ItemStackHelper.saveAllItems(compound, this.inventory);
			
		}

		return compound;
		
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		
		super.load(state, nbt);
		
		this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		
		if (!this.tryLoadLootTable(nbt)) {
			
			ItemStackHelper.loadAllItems(nbt, this.inventory);
			
		}

	}
	
	@Override
	public void startOpen(PlayerEntity player) {
		
		if (!player.isSpectator()) {
			
			if (this.numPlayersUsing < 0) {
				
				this.numPlayersUsing = 0;
				
			}

			++this.numPlayersUsing;
			
		}

	}
}
