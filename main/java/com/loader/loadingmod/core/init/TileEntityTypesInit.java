package com.loader.loadingmod.core.init;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.tileentities.ClickTileEntity;
import com.loader.loadingmod.common.tileentities.CustomCraftingTileEntity;
import com.loader.loadingmod.common.tileentities.ExchangerTileEntity;
import com.loader.loadingmod.common.tileentities.FurnaceTileEntity;
import com.loader.loadingmod.common.tileentities.TableTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
			.create(ForgeRegistries.TILE_ENTITIES, LoadingMod.MOD_ID);

	public static final RegistryObject<TileEntityType<ClickTileEntity>> CLICK_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("click_tile_entity",
					() -> TileEntityType.Builder.of(ClickTileEntity::new, BlockInit.click_block.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TableTileEntity>> TABLE_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("table_tile_entity",
					() -> TileEntityType.Builder.of(TableTileEntity::new, BlockInit.table_container.get()).build(null));
	
	public static final RegistryObject<TileEntityType<FurnaceTileEntity>> FURNACE_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("furnace_tile_entity", 
					() -> TileEntityType.Builder.of(FurnaceTileEntity::new, BlockInit.furnace_block.get()).build(null));
	
	public static final RegistryObject<TileEntityType<CustomCraftingTileEntity>> custom_crafting_block = TILE_ENTITY_TYPE
			.register("custom_crafting_tile_entity", 
			() -> TileEntityType.Builder.of(CustomCraftingTileEntity::new, BlockInit.custom_crafting_block.get()).build(null));
	
	public static final RegistryObject<TileEntityType<ExchangerTileEntity>> EXCHANGER_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE
			.register("exchanger_tile_entity", 
			() -> TileEntityType.Builder.of(ExchangerTileEntity::new, BlockInit.exchanger_block.get()).build(null));
}