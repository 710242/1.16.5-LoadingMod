package com.loader.loadingmod.core.init;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.containers.CustomCraftingContainer;
import com.loader.loadingmod.common.containers.ExchangerContainer;
import com.loader.loadingmod.common.containers.FurnaceContainer;
import com.loader.loadingmod.common.containers.TableContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeInit {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
			.create(ForgeRegistries.CONTAINERS, LoadingMod.MOD_ID);

	public static final RegistryObject<ContainerType<TableContainer>> TABLE_CONTAINER_TYPE = CONTAINER_TYPES
			.register("table", () -> IForgeContainerType.create(TableContainer::new));
	
	public static final RegistryObject<ContainerType<FurnaceContainer>> FURNACE_CONTAINER_TYPE = CONTAINER_TYPES
			.register("furnace", 
			() -> IForgeContainerType.create(FurnaceContainer::new));
	
	public static final RegistryObject<ContainerType<CustomCraftingContainer>> CUSTOM_CRAFTING = CONTAINER_TYPES
			.register("custom_crafting", 
			() -> IForgeContainerType.create(CustomCraftingContainer::new));
	
	public static final RegistryObject<ContainerType<ExchangerContainer>> EXCHANGER = CONTAINER_TYPES
			.register("exchanger", 
			() -> IForgeContainerType.create(ExchangerContainer::new));
}
