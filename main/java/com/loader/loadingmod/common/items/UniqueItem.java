package com.loader.loadingmod.common.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
//import net.tutorialsbykaupenjoe.tutorialmod.util.TutorialTags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class UniqueItem extends Item{
		public UniqueItem(Properties properties) {
	        super(properties);
	    }

		@Override
		public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		    World world = context.getLevel();
		
		    if(!world.isClientSide) {
		        PlayerEntity playerEntity = Objects.requireNonNull(context.getPlayer());
		        BlockState clickedBlock = world.getBlockState(context.getClickedPos());
		
		        rightClickOnCertainBlockState(clickedBlock, context, playerEntity);
		      //animateHurt(context.getHand())
		        stack.hurtAndBreak(1, playerEntity, player -> player.animateHurt());
		    }
		
		    return super.onItemUseFirst(stack, context);
		}

		@Override
		public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		    return super.use(worldIn, playerIn, handIn);
		}

		@Override
		public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		
		    if(Screen.hasShiftDown()) {
		        tooltip.add(new TranslationTextComponent("tooltip.loadingmod.unique_item_shift"));
		    } else {
		        tooltip.add(new TranslationTextComponent("tooltip.loadingmod.unique_item"));
		    }
		    super.appendHoverText(stack, worldIn, tooltip, flagIn);
		}
		
		private void rightClickOnCertainBlockState(BlockState clickedBlock, ItemUseContext context, PlayerEntity playerEntity) {
				boolean playerIsNotOnFire = !playerEntity.isOnFire();
		
		    if(random.nextFloat() > 0.5f) {
		        lightEntityOnFire(playerEntity, 6);
		        //&& blockIsValidForResistance(clickedBlock)
		    } else if(playerIsNotOnFire) {
		        gainFireResistanceAndDestroyBlock(playerEntity, context.getLevel(), context.getClickedPos());
		    } else {
//		        lightGroundOnFire(context);
		    }
		}

//		private boolean blockIsValidForResistance(BlockState clickedBlock) {
//		    return clickedBlock.isIn(TutorialTags.Blocks.FIRESTONE_CLICKABLE_BLOCKS);
//		}

		public static void lightEntityOnFire(Entity entity, int second) {
		    entity.setRemainingFireTicks(second);
		}

		private void gainFireResistanceAndDestroyBlock(PlayerEntity playerEntity, World world, BlockPos pos) {
		    gainFireResistance(playerEntity);
		    world.destroyBlock(pos, false);
		}

		public static void gainFireResistance(PlayerEntity playerEntity) {
		    playerEntity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 200));
		}

//		public static void lightGroundOnFire(ItemUseContext context) {
//		    PlayerEntity playerentity = context.getPlayer();
//		    World world = context.getLevel();
//		    // .offset(context.getFace()
//		    BlockPos blockpos = context.getClickedPos();
//		
//		    if (AbstractFireBlock.getLightBlock(world, blockpos, context.getPlacementHorizontalFacing())) {
//		        world.playSound(playerentity, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F,
//		                random.nextFloat() * 0.4F + 0.8F);
//		
//		        BlockState blockstate = AbstractFireBlock.getFireForPlacement(world, blockpos);
//		        world.setBlock(blockpos, blockstate, 11);
//		    }
//		}
		
//		@Override
//		public ItemStack getContainerItem(ItemStack itemStack) {
//		    ItemStack container = itemStack.copy();
//		    if(container.attemptDamageItem(1, random, null)) {
//		        return ItemStack.EMPTY;
//		    } else {
//		        return container;
//		    }
//		}
//
//		@Override
//		public boolean hasContainerItem(ItemStack stack) {
//		    return true;
//		}
}
