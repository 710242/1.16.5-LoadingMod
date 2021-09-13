package com.loader.loadingmod.common.blocks;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.loader.loadingmod.common.tileentities.CustomCraftingTileEntity;
import com.loader.loadingmod.core.init.TileEntityTypesInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CustomCraftBlock extends ContainerBlock implements IWaterLoggable{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE = Stream.of(Block.box(0, 12, 0, 16, 16, 16),
			Block.box(0, 0, 12, 4, 12, 16), Block.box(12, 0, 12, 16, 12, 16),
			Block.box(12, 0, 0, 16, 12, 4), Block.box(0, 0, 0, 4, 12, 4)).reduce((v1, v2) -> {
				return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
			}).get();

	public CustomCraftBlock() {
		super(Block.Properties.copy(Blocks.CRAFTING_TABLE));
		//this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, Boolean.valueOf(false)
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new CustomCraftingTileEntity();
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		
		if (worldIn.isClientSide) {
			
			return ActionResultType.SUCCESS;
			
		} else {
			
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			
			if (tileentity instanceof CustomCraftingTileEntity && player instanceof ServerPlayerEntity) {
				
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity, pos);
				player.awardStat(Stats.OPEN_BARREL);
				PiglinTasks.angerNearbyPiglins(player, true);
				
			}

			return ActionResultType.CONSUME;
			
		}
		
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		if (!state.is(newState.getBlock())) {
			
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			
			if (tileentity instanceof IInventory) {
				
				InventoryHelper.dropContents(worldIn, pos, (IInventory) tileentity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
				
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
			
		}
		
	}
	
	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		
		if (stack.getDisplayName() != null) {
			
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			
			if (tileentity instanceof CustomCraftingTileEntity) {
				
				((CustomCraftingTileEntity) tileentity).setCustomName(stack.getDisplayName());
				
			}
			
		}

	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		//getPos
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		return super.getStateForPlacement(context);
		
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		
		//get
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
