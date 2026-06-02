package com.github.guyapooye.porkchop_express.content.poultry.red;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlock;
import com.github.guyapooye.porkchop_express.registry.PEBlocks;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3d;

public class RedPoultryBlock extends PoultryBlock {
    
    public RedPoultryBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (stack.is(Items.GUNPOWDER)) {
            BlockState blockState = PEBlocks.EXPLOSIVE_POULTRY.get().defaultBlockState();
            blockState = blockState.setValue(FACING, state.getValue(FACING));
            level.setBlockAndUpdate(pos, blockState);
            stack.consume(1, player);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
    
    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }
}
