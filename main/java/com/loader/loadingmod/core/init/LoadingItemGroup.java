package com.loader.loadingmod.core.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LoadingItemGroup {
	public static final ItemGroup LOADING_GROUP = new ItemGroup("LoadingModTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.example_item.get());
        		}
    };
}
