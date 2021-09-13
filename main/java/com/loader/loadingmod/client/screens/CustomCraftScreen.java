package com.loader.loadingmod.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import com.loader.loadingmod.common.containers.CustomCraftingContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CustomCraftScreen extends ContainerScreen<CustomCraftingContainer>{
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("loadingmod:textures/gui/custom_crafting.png");

	public CustomCraftScreen(CustomCraftingContainer container, PlayerInventory playerInventory, ITextComponent title) {
		
		super(container, playerInventory, title);
		this.passEvents = false;
		this.imageHeight = 114 + 8 * 18;
		this.inventoryLabelY = this.imageHeight - 112;
		
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
		
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(GUI_TEXTURE);
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, i, j, 0, 0, this.imageWidth, 144);
		this.blit(matrixStack, i, j + 144, 0, 143, this.imageHeight, 97);
		
	}
}
