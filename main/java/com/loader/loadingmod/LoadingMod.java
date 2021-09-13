package com.loader.loadingmod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.loader.loadingmod.core.init.BlockInit;
import com.loader.loadingmod.core.init.ContainerTypeInit;
import com.loader.loadingmod.core.init.OreGenInit;
import com.loader.loadingmod.core.init.RecipeSerializerInit;
import com.loader.loadingmod.core.init.RecipeTypeInit;
import com.loader.loadingmod.core.init.ItemInit;
import com.loader.loadingmod.core.init.TileEntityTypesInit;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LoadingMod.MOD_ID)
public class LoadingMod
{
		// Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "loadingmod";
    
    public LoadingMod() {
        // Register the setup method for modloading
    		IEventBus eventbus = FMLJavaModLoadingContext.get().getModEventBus();
        		
        ItemInit.ITEMS.register(eventbus);
        TileEntityTypesInit.TILE_ENTITY_TYPE.register(eventbus);
        BlockInit.BLOCKS.register(eventbus);
        ContainerTypeInit.CONTAINER_TYPES.register(eventbus);
        RecipeSerializerInit.RECIPE_SERIALIZERS.register(eventbus);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGenInit::addOres);
    	}

//    @SubscribeEvent
//		public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
//			BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
//				event.getRegistry()
//						.register(new BlockItem(block, new Item.Properties().group(TutorialModItemGroup.TUTORIAL_MOD))
//								.setRegistryName(block.getRegistryName()));
//			});
//		}

}
