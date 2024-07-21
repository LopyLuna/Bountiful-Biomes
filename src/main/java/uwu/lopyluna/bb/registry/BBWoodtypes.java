package uwu.lopyluna.bb.registry;

import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.bb.BBMod;
import uwu.lopyluna.bb.content.block.FlammableBlock;
import uwu.lopyluna.bb.content.block.FlammableRotatedPillarBlock;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "SameParameterValue"})
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BBWoodtypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, BBMod.MOD_ID);

    static BootstapContext<ConfiguredFeature<?, ?>> contextConfiguredFeature;
    static BootstapContext<PlacedFeature> contextPlacedFeature;
    static BootstapContext<BiomeModifier> contextBiomeModifier;


    public static void setupWoodset(String Name,
                                    boolean Flammable, int Flammability, int FireSpreadSpeed,
                                    boolean hasSapling, boolean hasBoat, boolean hasSigns,
                                    TrunkPlacer pTrunkPlacer, FoliagePlacer pFoliagePlacer, FeatureSize pMinimumSize, TagKey<Biome> BiomeTag,
                                    Codec<? extends FoliagePlacer> FoliagePlacerCodec) {
        String id = Name.toLowerCase();
        WoodType Woodtype = WoodType.register(new WoodType(BBMod.MOD_ID + ":" + id, BlockSetType.MANGROVE));
        DeferredRegister<Block> BlockReg = BBBlocks.BLOCKS;
        DeferredRegister<Item> ItemReg = BBItems.ITEMS;

        final RegistryObject<Block> LOG = registerBlock(id + "_log",
                () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f), Flammable, (int)(Flammability / 4.0F), FireSpreadSpeed), BlockReg, ItemReg);
        final RegistryObject<Block> WOOD = registerBlock(id + "_wood",
                () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f), Flammable, (int)(Flammability / 4.0F), FireSpreadSpeed), BlockReg, ItemReg);
        final RegistryObject<Block> STRIPPED_LOG = registerBlock("stripped_" + id + "_log",
                () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f), Flammable, (int)(Flammability / 4.0F), FireSpreadSpeed), BlockReg, ItemReg);
        final RegistryObject<Block> STRIPPED_WOOD = registerBlock("stripped_" + id + "_wood",
                () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f), Flammable, (int)(Flammability / 4.0F), FireSpreadSpeed), BlockReg, ItemReg);

        final RegistryObject<Block> PLANKS = registerBlock(id + "_planks",
                () -> new FlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), Flammable, Flammability, FireSpreadSpeed), BlockReg, ItemReg);

        final RegistryObject<Block> LEAVES = registerBlock(id + "_leaves",
                () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
                    @Override public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {return Flammable;}
                    @Override public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {return (int)(Flammability * 3.0F);}
                    @Override public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {return (int)(FireSpreadSpeed * 6.0F);}}, BlockReg, ItemReg);


        //if (hasSigns) {
        //    final RegistryObject<Block> SIGN = BlockReg.register(id + "_sign",
        //            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), Woodtype));
        //    final RegistryObject<Block> WALL_SIGN = BlockReg.register(id + "_wall_sign",
        //            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), Woodtype));
        //
        //    final RegistryObject<Block> HANGING_SIGN = BlockReg.register(id + "_hanging_sign",
        //            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), Woodtype));
        //    final RegistryObject<Block> WALL_HANGING_SIGN = BlockReg.register(id + "_wall_hanging_sign",
        //            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), Woodtype));
        //
        //    final RegistryObject<Item> SIGN_ITEM = ItemReg.register(id + "_sign",
        //            () -> new SignItem(new Item.Properties().stacksTo(16), SIGN.get(), WALL_SIGN.get()));
        //    final RegistryObject<Item> HANGING_SIGN_ITEM = ItemReg.register(id + "_hanging_sign",
        //            () -> new HangingSignItem(HANGING_SIGN.get(), WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
        //}

        //if (hasBoat) {
        //
        //    final RegistryObject<Item> BOAT_ITEM = ItemReg.register(id + "_boat",
        //            () -> new BoatItem(false, BoatEntityTypeWood, new Item.Properties()));
        //    final RegistryObject<Item> CHEST_BOAT_ITEM = ItemReg.register(id + "_chest_boat",
        //            () -> new BoatItem(true, BoatEntityTypeWood, new Item.Properties()));
        //}

        if (hasSapling) {
            final ResourceKey<ConfiguredFeature<?, ?>> KEY = registerConfiguredFeatureKey(id);
            final ResourceKey<PlacedFeature> PLACED_KEY = registerPlacedKey(id + "_placed");
            final ResourceKey<BiomeModifier> ADD_TREE = registerBiomeModifierKey("add_tree" + id);

            final RegistryObject<Block> SAPLING = registerBlock(id + "_sapling",
                    () -> new SaplingBlock(new AbstractTreeGrower(){
                        @Override
                        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
                            return KEY;
                        }
                    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)
                            .mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), BlockReg, ItemReg);

            final RegistryObject<FoliagePlacerType<? extends FoliagePlacer>> FOLIAGE_PLACER =
                    FOLIAGE_PLACERS.register(id + "_foliage_placer", () -> new FoliagePlacerType<>(FoliagePlacerCodec));

            registerConfiguredFeatureKey(contextConfiguredFeature, KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(LOG.get()),
                    pTrunkPlacer,
                    BlockStateProvider.simple(LEAVES.get()),
                    pFoliagePlacer,
                    pMinimumSize
            ).build());

            HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = contextPlacedFeature.lookup(Registries.CONFIGURED_FEATURE);

            registerPlacedFeature(contextPlacedFeature, PLACED_KEY, configuredFeatures.getOrThrow(KEY),
                    VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                            SAPLING.get()));

            var placedFeatures = contextBiomeModifier.lookup(Registries.PLACED_FEATURE);
            var biomes = contextBiomeModifier.lookup(Registries.BIOME);

            contextBiomeModifier.register(ADD_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                    biomes.getOrThrow(BiomeTag),
                    HolderSet.direct(placedFeatures.getOrThrow(PLACED_KEY)),
                    GenerationStep.Decoration.VEGETAL_DECORATION));
        }

    }


    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, DeferredRegister<Block> BlockReg, DeferredRegister<Item> ItemReg) {
        RegistryObject<T> toReturn = BlockReg.register(name, block);
        registerBlockItem(name, toReturn, ItemReg);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, DeferredRegister<Item> ItemReg) {
        ItemReg.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerConfiguredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, BBMod.resLoc(name));
    }

    private static ResourceKey<PlacedFeature> registerPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, BBMod.resLoc(name));
    }

    private static ResourceKey<BiomeModifier> registerBiomeModifierKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, BBMod.resLoc(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeatureKey(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
    private static void registerPlacedFeature(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static void configuredFeatureBootstrap(BootstapContext<ConfiguredFeature<?, ?>> pContext) {
        contextConfiguredFeature = pContext;
    }
    public static void placedFeatureBootstrap(BootstapContext<PlacedFeature> pContext) {
        contextPlacedFeature = pContext;
    }

    public static void biomeModifierBootstrap(BootstapContext<BiomeModifier> pContext) {
        contextBiomeModifier = pContext;
    }
}
