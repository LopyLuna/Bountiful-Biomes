package uwu.lopyluna.bb.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableBlock extends Block {
    public boolean isFlammable;
    public int getFlammability;
    public int getFireSpreadSpeed;

    public FlammableBlock(Properties pProperties, boolean Flammable, int Flammability, int FireSpreadSpeed) {
        super(pProperties);
        isFlammable = Flammable;
        getFlammability = Flammability;
        getFireSpreadSpeed = FireSpreadSpeed;
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return isFlammable;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getFlammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return getFireSpreadSpeed;
    }
}
