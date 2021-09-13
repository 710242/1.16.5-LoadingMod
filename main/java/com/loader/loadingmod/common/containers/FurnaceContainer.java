package com.loader.loadingmod.common.containers;

import com.loader.loadingmod.core.init.RecipeTypeInit;
import com.loader.loadingmod.core.init.ContainerTypeInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;

public class FurnaceContainer extends AbstractFurnaceContainer {
	
	public FurnaceContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray array) {
		
		super(ContainerTypeInit.FURNACE_CONTAINER_TYPE.get(), RecipeTypeInit.SMELTING, RecipeBookCategory.FURNACE, id, playerInventory, inventory, array);
		
	}
	
	public FurnaceContainer(int id, PlayerInventory playerInventory, PacketBuffer extraData) {
		
		super(ContainerTypeInit.FURNACE_CONTAINER_TYPE.get(), RecipeTypeInit.SMELTING, RecipeBookCategory.FURNACE, id, playerInventory);
		
	}
	
	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return true;
	}

}