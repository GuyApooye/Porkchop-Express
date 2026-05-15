package com.github.guyapooye.porkchop_express.content.poultry;

import com.github.guyapooye.porkchop_express.ext.poultry.LevelPoultryExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PoultryBlockEntity extends BlockEntity {
    
    public PoultryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    
    public void onCreate(Level level) {
        if (level != null) {
            ((LevelPoultryExtension) level).porkchop_express$getPoultryManager().birds.add(this.getBlockPos());
        }
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        this.onCreate(this.level);
    }
}
