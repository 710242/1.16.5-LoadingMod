package com.loader.loadingmod.core.init;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.recipe.ExampleRecipe;
import com.loader.loadingmod.common.recipe.SmeltingRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeTypeInit {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LoadingMod.MOD_ID);	
	
	//register name must be as same as the one in RecipeSerializerInit
	public static final IRecipeType<SmeltingRecipe> SMELTING = IRecipeType.register("loading_smelting");
	
	public static final IRecipeType<ExampleRecipe> EXAMPLE = IRecipeType.register("example");
}
