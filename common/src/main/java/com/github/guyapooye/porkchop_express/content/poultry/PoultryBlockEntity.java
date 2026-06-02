package com.github.guyapooye.porkchop_express.content.poultry;

import com.github.guyapooye.porkchop_express.ext.poultry.LevelPoultryExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PoultryBlockEntity extends BlockEntity {
    
    protected int despawnTicks = -1;
    
    public PoultryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    
    protected void tick() {
        if (this.despawnTicks > 0) {
            this.despawnTicks--;
        }
        
        if (this.despawnTicks == 0 && this.level != null) {
            this.level.destroyBlock(this.getBlockPos(), false);
        }
    }
    
    public void despawnSoon() {
        this.despawnTicks = 1200;
    }
    
    public void onCreate() {
        if (this.level != null) {
            ((LevelPoultryExtension) this.level).porkchop_express$getPoultryManager().birds.add(this.getBlockPos());
        }
    }
}
