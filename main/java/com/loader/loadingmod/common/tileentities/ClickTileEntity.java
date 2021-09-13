package com.loader.loadingmod.common.tileentities;

import com.loader.loadingmod.core.init.TileEntityTypesInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ClickTileEntity extends TileEntity implements ITickableTileEntity {
	
	private int counter = 0;
	
	public ClickTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public ClickTileEntity() {
		this(TileEntityTypesInit.CLICK_TILE_ENTITY_TYPE.get());
	}
	
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
	    super.load(state, nbt);
	
	    this.setCounter(nbt.getInt("counter"));
    }
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
	    super.save(compound);
	
	    compound.putInt("counter", this.getCounter());
	
	    return compound;
    }

	@Override
	public void tick() {
			this.level.setBlock(this.worldPosition.below(), Blocks.AIR.defaultBlockState(), 0);
		  this.setCounter(getCounter()+1);
	}
	public int getCounter() {
	    return counter;
    }
	public void setCounter(int counter) {
        this.counter = counter;
    }
}