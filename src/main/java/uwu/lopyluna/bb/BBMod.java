package uwu.lopyluna.bb;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
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

        BBWoodtypes.setupWoodset("Mahogany",
                true, 20, 5,
                true, true, true);

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
