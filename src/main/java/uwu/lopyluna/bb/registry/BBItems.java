package uwu.lopyluna.bb.registry;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.bb.BBMod;
import uwu.lopyluna.bb.annotations.NoTab;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "SameParameterValue"})
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BBItems {
    private static final Supplier<Item> SS = () -> new Item(new Item.Properties());
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BBMod.MOD_ID);







    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BBMod.MOD_ID);
    private static final RegistryObject<CreativeModeTab> BB_TAB;

    static {
        BB_TAB = registerTabSearchBar(BBMod.NAME, "bb_tab", Blocks.CHERRY_LOG, output -> {
            for (RegistryObject<? extends Item> registry : getItems()) {
                if (registry.get() instanceof BlockItem || registry.get().getClass().isAnnotationPresent(NoTab.class)) continue;
                output.accept(registry.get());
            }
            for (RegistryObject<? extends Block> registry : BBBlocks.getBlocks()) {
                if (registry.get().getClass().isAnnotationPresent(NoTab.class)) continue;
                output.accept(registry.get());
            }
        }, builder -> builder.withTabsBefore(CreativeModeTabs.COMBAT));
    }

    private static RegistryObject<CreativeModeTab>
    registerTabSearchBar(String tabName, String name, ItemLike icon, Consumer<CreativeModeTab.Output> displayItems, Consumer<CreativeModeTab.Builder> additionalProperties) {
        return CREATIVE_MODE_TABS.register(name, () -> { final CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.literal(tabName))
                    .icon(() -> new ItemStack(icon))
                    .withSearchBar()
                    .displayItems((pParameters, pOutput) -> displayItems.accept(pOutput));
            additionalProperties.accept(builder);
            return builder.build();
        });
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }
    public static Collection<RegistryObject<Item>> getItems() {
        return ITEMS.getEntries();
    }
}
