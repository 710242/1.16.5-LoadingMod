package com.loader.loadingmod.common.blocks.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import com.loader.loadingmod.world.gen.ModConfiguredFeatures;

import javax.annotation.Nullable;
import java.util.Random;

public class WoodTree extends Tree{
	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
	    return ModConfiguredFeatures.WOODTREE;
    }
}
