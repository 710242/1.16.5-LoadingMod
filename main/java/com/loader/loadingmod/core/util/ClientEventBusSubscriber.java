package com.loader.loadingmod.core.util;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.client.screens.TableScreen;
import com.loader.loadingmod.client.screens.FurnaceScreen;
import com.loader.loadingmod.client.screens.CustomCraftScreen;
import com.loader.loadingmod.client.screens.ExchangerScreen;
import com.loader.loadingmod.client.render.tileentity.*;
import com.loader.loadingmod.core.init.BlockInit;
import com.loader.loadingmod.core.init.ContainerTypeInit;
import com.loader.loadingmod.core.init.TileEntityTypesInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LoadingMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		
		ScreenManager.register(ContainerTypeInit.TABLE_CONTAINER_TYPE.get(), TableScreen::new);
		
		ScreenManager.register(ContainerTypeInit.FURNACE_CONTAINER_TYPE.get(), FurnaceScreen::new);
		
		ScreenManager.register(ContainerTypeInit.CUSTOM_CRAFTING.get(), CustomCraftScreen::new);
		
		ScreenManager.register(ContainerTypeInit.EXCHANGER.get(), ExchangerScreen::new);
		
		ClientRegistry.bindTileEntityRenderer(TileEntityTypesInit.TABLE_TILE_ENTITY_TYPE.get(), TableTileEntityRenderer::new);
		
	}
}
