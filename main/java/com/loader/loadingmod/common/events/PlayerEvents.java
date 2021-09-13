package com.loader.loadingmod.common.events;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.core.init.BlockInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = LoadingMod.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {
	
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		player.inventory.armor.set(0, new ItemStack(Items.GOLDEN_HELMET));
	}
	
	@SubscribeEvent
	public static void onPlayerToss(ItemTossEvent event) {
		PlayerEntity player = event.getPlayer();
		World world = player.getCommandSenderWorld();
		BlockState state = world.getBlockState(player.blockPosition().below());
		
		if (state.getBlock() == BlockInit.wood_wood.get()) {
			player.addItem(new ItemStack(Items.DIAMOND));
		}
	}
}
