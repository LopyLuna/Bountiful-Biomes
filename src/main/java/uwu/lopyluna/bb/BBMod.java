package uwu.lopyluna.bb;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import uwu.lopyluna.bb.registry.BBBlocks;
import uwu.lopyluna.bb.registry.BBItems;
import uwu.lopyluna.bb.registry.BBWoodtypes;

import java.util.Random;

@SuppressWarnings({"removal","all"})
@Mod(BBMod.MOD_ID)
public class BBMod {
    public static final String NAME = "Bountiful Biomes";
    public static final String MOD_ID = "bb";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Random RANDOM = new Random();

    public BBMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BBItems.register(modEventBus);
        BBBlocks.register(modEventBus);
        BBWoodtypes.register(modEventBus);

        BBWoodtypes.setupWoodset("Mahogany",
                true, 20, 5,
                true, true, true,
                new CherryTrunkPlacer( //hehehe ha
                        7, //pBaseHeight
                        1, //pHeightRandA
                        0, //pHeightRandB
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder() //branchCount
                                .add(ConstantInt.of(3), 10)
                                .add(ConstantInt.of(4), 8)
                                .add(ConstantInt.of(5), 6)
                                .add(ConstantInt.of(6), 1)
                                .build()),
                        UniformInt.of(3, 5), //branchHorizontalLength
                        UniformInt.of(3, 7), //branchStartOffsetFromTop & secondBranchStartOffsetFromTop
                        UniformInt.of(2, 4)), // branchEndOffsetFromTop
                new CherryFoliagePlacer(
                        ConstantInt.of(4),
                        ConstantInt.of(0),
                        ConstantInt.of(5),
                        0.25F,
                        0.5F,
                        0.16666667F,
                        0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2),
                Tags.Biomes.IS_WET_OVERWORLD,
                PineFoliagePlacer.CODEC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    public static ResourceLocation resLoc(String path) {
        return new ResourceLocation(BBMod.MOD_ID, path);
    }
}
