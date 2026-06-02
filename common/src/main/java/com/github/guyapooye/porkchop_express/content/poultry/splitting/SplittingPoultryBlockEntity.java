package com.github.guyapooye.porkchop_express.content.poultry.splitting;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SplittingPoultryBlockEntity extends PoultryBlockEntity {

    public SplittingPoultryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        if (blockState.getValue(SplittingPoultryBlock.CLONE)) {
            this.despawnSoon();
        }
    }


}
