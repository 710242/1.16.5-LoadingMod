package com.loader.loadingmod.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.containers.ExchangerContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ExchangerScreen extends ContainerScreen<ExchangerContainer>{
	private static final ResourceLocation TEXTURE = new ResourceLocation(LoadingMod.MOD_ID,
			"textures/gui/example_furnace.png");

	public ExchangerScreen(ExchangerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.textureManager.bind(TEXTURE);
		this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		this.blit(matrixStack, this.leftPos + 79, this.topPos + 35, 176, 0, this.menu.getSmeltProgressionScaled(), 16);
	}

	@Override
	protected void renderLabels(MatrixStack matrixStack, int x, int y) {
		super.renderLabels(matrixStack, x, y);
		this.font.draw(matrixStack, this.title, 8.0f, 8.0f, 0x404040);
		this.font.draw(matrixStack, this.inventory.getDisplayName(), 8.0f, 69.0f, 0x404040);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
}
