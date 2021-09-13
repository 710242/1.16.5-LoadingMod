package com.loader.loadingmod.common.recipe;

import javax.annotation.Nonnull;

import com.loader.loadingmod.LoadingMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IExampleRecipe extends IRecipe<RecipeWrapper>{
	ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(LoadingMod.MOD_ID, "example");
	
	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.get(RECIPE_TYPE_ID);
	}
	
	@Override
	default boolean canCraftInDimensions(int width, int height) {
		return false;
	}
	
	Ingredient getInput();
}
