package com.loader.loadingmod.common.tileentities;

import com.loader.loadingmod.common.containers.FurnaceContainer;
import com.loader.loadingmod.core.init.RecipeTypeInit;
import com.loader.loadingmod.core.init.TileEntityTypesInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FurnaceTileEntity extends AbstractFurnaceTileEntity{
	
	public FurnaceTileEntity() {
		super(TileEntityTypesInit.FURNACE_TILE_ENTITY_TYPE.get(), RecipeTypeInit.SMELTING);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.smelting_furnace");
	}
	
	@Override
	protected int getBurnDuration(ItemStack fuel) {
		return super.getBurnDuration(fuel) / 2;
	}
	
	@Override
	protected Container createMenu(int id, PlayerInventory  player) {
		return new FurnaceContainer(id, player, this, this.dataAccess);
	}
	
}
