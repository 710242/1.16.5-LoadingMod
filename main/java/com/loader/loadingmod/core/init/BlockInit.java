package com.loader.loadingmod.core.init;

import java.util.function.Supplier;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.blocks.ClickBlock;
import com.loader.loadingmod.common.blocks.CustomCraftBlock;
import com.loader.loadingmod.common.blocks.ExchangerBlock;
import com.loader.loadingmod.common.blocks.FurnaceBlock;
import com.loader.loadingmod.common.blocks.TableContainerBlock;
import com.loader.loadingmod.common.blocks.trees.WoodTree;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LoadingMod.MOD_ID);
	
	// this item property can add as "Item.Properties().blablabla"
	public static final RegistryObject<Block> ore_block = registerBlock("ore_block",
			 () -> new Block(AbstractBlock.Properties.of(Material.STONE)
	                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).strength(3).requiresCorrectToolForDrops()));
	
	public static final RegistryObject<Block> click_block = registerBlock("click_block",
			 () -> new ClickBlock());
	
	public static final RegistryObject<Block> table_container = registerBlock("table_container",
			 () -> new TableContainerBlock());
	
	public static final RegistryObject<Block> furnace_block = registerBlock("furnace_block", 
			() -> new FurnaceBlock());
	
	public static final RegistryObject<Block> exchanger_block = registerBlock("exchanger_block", 
			() -> new ExchangerBlock());
	
	public static final RegistryObject<Block> custom_crafting_block = registerBlock("custom_crafting_block",
			() -> new CustomCraftBlock());
	
	public static final RegistryObject<Block> wood_log = registerBlock("wood_log",
			 () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_LOG)));
	
 public static final RegistryObject<Block> stripped_wood_log = registerBlock("stripped_wood_log",
		 	() -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
 
 public static final RegistryObject<Block> wood_wood = registerBlock("wood_wood",
         () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.OAK_WOOD)));
 
 public static final RegistryObject<Block> stripped_wood_wood = registerBlock("stripped_wood_wood",
         () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
 
	public static final RegistryObject<Block> wood_plank = registerBlock("wood_plank",
			 () -> new Block(AbstractBlock.Properties.of(Material.WOOD)
	                    .harvestLevel(2).harvestTool(ToolType.AXE).strength(3).requiresCorrectToolForDrops()));
	
	public static final RegistryObject<Block> wood_leaves = registerBlock("wood_leaves",
			 () -> new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES)
	                    .harvestLevel(2).harvestTool(ToolType.AXE).strength(3).requiresCorrectToolForDrops()));
	
	public static final RegistryObject<Block> wood_sapling = registerBlock("wood_sapling",
			 () -> new SaplingBlock(new WoodTree(), AbstractBlock.Properties.copy(Blocks.OAK_SAPLING) ));
	
	
	
	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
	    RegistryObject<T> toReturn = BLOCKS.register(name, block);
	    registerBlockItem(name, toReturn);
	    return toReturn;
    }
	private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(LoadingItemGroup.LOADING_GROUP)));
    }
	
	public static void register(IEventBus eventbus) {
		BLOCKS.register(eventbus);
	}
}
