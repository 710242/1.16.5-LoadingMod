package com.loader.loadingmod.world.gen;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import com.loader.loadingmod.core.init.BlockInit;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> WOODTREE =
            register("redwood", Feature.TREE.configured((
                        new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(BlockInit.wood_log.get().defaultBlockState()),
                            new SimpleBlockStateProvider(BlockInit.wood_leaves.get().defaultBlockState()),
                         // origin fixed is create
                            new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
                            new StraightTrunkPlacer(6, 4, 3),
                            new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));


    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key,
                                                                                 ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}