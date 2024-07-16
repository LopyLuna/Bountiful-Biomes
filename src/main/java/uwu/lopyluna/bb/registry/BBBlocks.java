package uwu.lopyluna.bb.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.bb.BBMod;

import java.util.Collection;

@SuppressWarnings("unused")
public class BBBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BBMod.MOD_ID);





    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
    public static Collection<RegistryObject<Block>> getBlocks() {
        return BLOCKS.getEntries();
    }
}
