package com.github.guyapooye.porkchop_express.content.poultry.explosive;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import foundry.veil.api.network.VeilPacketManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosivePoultryBlockEntity extends PoultryBlockEntity {
    
    protected float fuze = -1.0f;
    protected float lastFuze = -1.0f;
    
    public ExplosivePoultryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    
    public void setFuze(int i) {
        this.fuze = i;
        this.lastFuze = i;
        if (this.level != null && this.level.isClientSide()) {
            return;
        }
        VeilPacketManager.tracking(this).sendPacket(
            new FuzeSyncPacket(this.getBlockPos(), i)
        );
    }
    
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putFloat("Fuze", this.fuze);
    }
    
    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.lastFuze = this.fuze = pTag.getFloat("Fuze");
    }
}
