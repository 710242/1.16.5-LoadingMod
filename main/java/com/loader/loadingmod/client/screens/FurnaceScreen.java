package com.loader.loadingmod.client.screens;

import com.loader.loadingmod.common.containers.FurnaceContainer;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FurnaceScreen extends AbstractFurnaceScreen<FurnaceContainer>{
	private static final ResourceLocation GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");

	public FurnaceScreen(FurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		
		super(screenContainer, new FurnaceRecipeGui(), inv, titleIn, GUI_TEXTURES);
		
	}
}
