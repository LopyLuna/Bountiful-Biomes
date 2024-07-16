package uwu.lopyluna.bb.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import uwu.lopyluna.bb.BBMod;
import uwu.lopyluna.bb.content.block.FlammableBlock;
import uwu.lopyluna.bb.content.block.FlammableRotatedPillarBlock;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BBWoodtypes {



    public static void setupWoodset(String Name,
                                    boolean Flammable, int Flammability, int FireSpreadSpeed,
                                    boolean hasSapling, boolean hasBoat, boolean hasSigns) {
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

        //if (hasSapling) {
        //    final RegistryObject<Block> SAPLING = registerBlock(id + "_sapling",
        //            () -> new SaplingBlock(new WoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), BlockReg, ItemReg);
        //}

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
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, DeferredRegister<Block> BlockReg, DeferredRegister<Item> ItemReg) {
        RegistryObject<T> toReturn = BlockReg.register(name, block);
        registerBlockItem(name, toReturn, ItemReg);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, DeferredRegister<Item> ItemReg) {
        ItemReg.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
