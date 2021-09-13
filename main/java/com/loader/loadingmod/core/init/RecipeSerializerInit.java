package com.loader.loadingmod.core.init;

import com.loader.loadingmod.LoadingMod;
import com.loader.loadingmod.common.recipe.ExampleRecipe;
import com.loader.loadingmod.common.recipe.IExampleRecipe;
import com.loader.loadingmod.common.recipe.SmeltingRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerInit {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LoadingMod.MOD_ID);
	
	//register name must be as same as the one in RecipeTypeInit
	public static final RegistryObject<IRecipeSerializer<?>> SMELTING = RECIPE_SERIALIZERS.register("loading_smelting", 
			() -> new SmeltingRecipe.Serializer());
	
	public static final RegistryObject<IRecipeSerializer<?>> EXAMPLE_SERIALIZER = RECIPE_SERIALIZERS.register("example", 
			() -> new ExampleRecipe.Serializer());
	
	public static final IRecipeType<IExampleRecipe> EXAMPLE_TYPE = registerType(IExampleRecipe.RECIPE_TYPE_ID);
	
	
	private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
		@Override
		public String toString() {
			return Registry.RECIPE_TYPE.getKey(this).toString();
		}
	}
	
	private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId) {
		return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
	}
}
