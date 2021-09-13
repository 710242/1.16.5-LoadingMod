package com.loader.loadingmod.common.blocks;

import com.loader.loadingmod.common.tileentities.ClickTileEntity;
import com.loader.loadingmod.common.tileentities.FurnaceTileEntity;
import com.loader.loadingmod.core.init.TileEntityTypesInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
 
public class ClickBlock extends Block {

	public ClickBlock() {
		super(AbstractBlock.Properties.of(Material.HEAVY_METAL, MaterialColor.COLOR_GRAY)
				.sound(SoundType.METAL));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.CLICK_TILE_ENTITY_TYPE.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	
				if(worldIn.getBlockEntity(pos) instanceof ClickTileEntity)
		        {
							 ClickTileEntity te = (ClickTileEntity) worldIn.getBlockEntity(pos);
	            player.displayClientMessage(new StringTextComponent("Counter : " + te.getCounter()), true);
	            return ActionResultType.SUCCESS;
		        }
	
	    return ActionResultType.PASS;
    }

}