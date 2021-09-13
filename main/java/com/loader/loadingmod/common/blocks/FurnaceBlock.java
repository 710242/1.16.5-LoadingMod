package com.loader.loadingmod.common.blocks;

import java.util.Random;

import com.loader.loadingmod.common.tileentities.FurnaceTileEntity;
import com.loader.loadingmod.core.init.TileEntityTypesInit;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class FurnaceBlock extends AbstractFurnaceBlock{
	
	public FurnaceBlock() {	
		super(AbstractBlock.Properties.of(Material.STONE)
				.strength(3.5f)
				.sound(SoundType.STONE)
				.harvestTool(ToolType.PICKAXE)
				.requiresCorrectToolForDrops());
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return TileEntityTypesInit.FURNACE_TILE_ENTITY_TYPE.get().create();
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new FurnaceTileEntity();
	}

	@Override
	protected void openContainer(World worldIn, BlockPos pos, PlayerEntity player) {
		
		TileEntity tileentity = worldIn.getBlockEntity(pos);
		
		if (player instanceof ServerPlayerEntity && tileentity instanceof FurnaceTileEntity) {
			
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity, pos);
			
		}
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		
		//get
		if (stateIn.getValue(LIT)) {
			
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY();
			double d2 = (double) pos.getZ() + 0.5D;
			
			if (rand.nextDouble() < 0.1D) {
				
				worldIn.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
				
			}

			Direction direction = stateIn.getValue(FACING);
			Direction.Axis direction$axis = direction.getAxis();
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
			double d6 = rand.nextDouble() * 9.0D / 16.0D;
			double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
			worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
			
		}
		
	}
}
