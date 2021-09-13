package com.loader.loadingmod.common.containers;

import java.util.Optional;

import com.loader.loadingmod.core.init.ContainerTypeInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeBookCategory;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.world.World;

public class CustomCraftingContainer extends RecipeBookContainer<CraftingInventory>{
	private final CraftingInventory craftMatrix = new CraftingInventory(this, 3, 3);
	private final CraftResultInventory craftResult = new CraftResultInventory();
	private final PlayerEntity player;
	
	public CustomCraftingContainer(int id, PlayerInventory playerInventory, PacketBuffer extraData) {
		
		this(id, playerInventory, new Inventory(4 * 7 + 10));
		
	}
	
	public CustomCraftingContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
		
		super(ContainerTypeInit.CUSTOM_CRAFTING.get(), id);
		this.player = playerInventory.player;
		
		this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 116, 112));
		
		for (int i = 0; i < 7; ++i) {
			
			for (int j = 0; j < 4; j++) {
				
				this.addSlot(new Slot(inventory, j + i * 4, 8 + j * 18, 18 + i * 18));
				
			}
			
		}
		
		for (int i = 0; i < 3; ++i) {
			
			for (int j = 0; j < 3; ++j) {
				
				this.addSlot(new Slot(this.craftMatrix, j + i * 3, (j * 18) + 98, (i * 18) + 18));
				
			}
			
		}
		
		for (int i = 0; i < 3; ++i) {
			
			for (int j = 0; j < 9; ++j) {
				
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, (i * 18) + 159));
				
			}
			
		}
		
		for (int i = 0; i < 9; ++i) {
			
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 217));
			
		}
		
	}
	
	@Override
	public void slotsChanged(IInventory inventoryIn) {
		
		super.slotsChanged(inventoryIn);		
		World world = player.getCommandSenderWorld();
		
		if (!world.isClientSide) {
			
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
			ItemStack itemstack = ItemStack.EMPTY;
			Optional<ICraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(IRecipeType.CRAFTING, craftMatrix, world);
			
			if (optional.isPresent()) {
				
				ICraftingRecipe icraftingrecipe = optional.get();
				
				if (craftResult.setRecipeUsed(world, serverplayerentity, icraftingrecipe)) {
					
					itemstack = icraftingrecipe.assemble(craftMatrix);
					
				}
				
			}

			craftResult.setItem(0, itemstack);
			serverplayerentity.connection.send(new SSetSlotPacket(this.containerId, 0, itemstack));
			
		}
		
	}

	@Override
	public void fillCraftSlotsStackedContents(RecipeItemHelper itemHelperIn) { 
		this.craftMatrix.fillStackedContents(itemHelperIn);
	}
	
	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {

		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if (slot != null && slot.hasItem()) {

			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			if (index == 0) {
				
				itemstack1.getItem().onCraftedBy(itemstack1, player.getCommandSenderWorld(), playerIn);
				if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {

					return ItemStack.EMPTY;

				}

				slot.onQuickCraft(itemstack1, itemstack);

			} else if (index < (4 * 7 + 10)) {
				
				//inventorySlots().size()
				if (!this.moveItemStackTo(itemstack1, 4 * 7 + 10, this.slots.size(), true)) {

					return ItemStack.EMPTY;

				}

			} else if (!this.moveItemStackTo(itemstack1, 0, 4 * 7 + 10, false)) {

				return ItemStack.EMPTY;

			}

			if (itemstack1.isEmpty()) {

				slot.set(ItemStack.EMPTY);

			} else {

				slot.setChanged();

			}
			
			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
			
			if (index == 0) {
				
				playerIn.drop(itemstack2, false);
				
			}

		}

		return itemstack;
		
	}

	@Override
	public void removed(PlayerEntity playerIn) {
		
		super.removed(playerIn);
		InventoryHelper.dropContents(player.level, player, craftMatrix);
		
	}
	
	@Override
	public void clearCraftingContent() {
		
		this.craftMatrix.clearContent();
		this.craftResult.clearContent();
		
	}

	@Override
	public boolean recipeMatches(IRecipe<? super CraftingInventory> recipe) {
		
		return recipe.matches(this.craftMatrix, player.level);
		
	}

	@Override
	public int getResultSlotIndex() {
		return 0;
	}

	@Override
	public int getGridWidth() {
		return this.craftMatrix.getWidth();
	}

	@Override
	public int getGridHeight() {
		return this.craftMatrix.getWidth();
	}

	@Override
	public int getSize() {
		return 4 * 7 + 10;
	}

	@Override
	public RecipeBookCategory getRecipeBookType() {
		return RecipeBookCategory.CRAFTING;
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return true;
	}

}
