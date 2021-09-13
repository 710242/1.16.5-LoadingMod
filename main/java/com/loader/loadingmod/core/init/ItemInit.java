package com.loader.loadingmod.core.init;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.items.TeleportWand;
import com.loader.loadingmod.common.items.UniqueItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
		public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LoadingMod.MOD_ID);
		
		// this item property can add as "Item.Properties().blablabla"
		public static final RegistryObject<Item> example_item = ITEMS.register("example_item",
				()-> new Item(new Item.Properties().tab(LoadingItemGroup.LOADING_GROUP)));
		
		public static final RegistryObject<Item> unique_item = ITEMS.register("unique_item",
				()-> new UniqueItem(new Item.Properties().tab(LoadingItemGroup.LOADING_GROUP)));
		
		public static final RegistryObject<Item> teleport_wand = ITEMS.register("teleport_wand",
				()-> new TeleportWand(new Item.Properties().tab(LoadingItemGroup.LOADING_GROUP)));
		
		public static void register(IEventBus eventbus) {
			ITEMS.register(eventbus);
		}
}
