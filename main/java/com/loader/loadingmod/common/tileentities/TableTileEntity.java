package com.loader.loadingmod.common.tileentities;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.containers.TableContainer;
import com.loader.loadingmod.core.init.BlockInit;
import com.loader.loadingmod.core.init.ItemInit;
import com.loader.loadingmod.core.init.TileEntityTypesInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TableTileEntity extends LockableLootTileEntity{
	public static int slots = 1;
	
	protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

	protected TableTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}
	
	public TableTileEntity() {
		this(TileEntityTypesInit.TABLE_TILE_ENTITY_TYPE.get());
	}

//	@Override
	public int getContainerSize() {
		return slots;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	public ItemStack getItem() {
		return this.items.get(0);
	}
	
	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		this.items = itemsIn;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + LoadingMod.MOD_ID + ".display_case");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new TableContainer(id, player, this);
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		if(!this.trySaveLootTable(compound)) {
			ItemStackHelper.saveAllItems(compound, this.items);
		}
		
		return compound;
	}
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
		if (!this.tryLoadLootTable(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.items);
		}
	}
}
