package com.github.guyapooye.porkchop_express.content.poultry.explosive;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.SubLevel;
import foundry.veil.api.network.VeilPacketManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class ExplosivePoultryBlockEntity extends PoultryBlockEntity {
    
    protected float fuze = -1.0f;
    protected float lastFuze = -1.0f;
    
    public ExplosivePoultryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    
    @Override
    protected void tick() {
        super.tick();
        if (this.fuze > 0) {
            this.fuze--;
            this.lastFuze = this.fuze;
        }
        if (this.fuze == 0 && this.level != null) {
            SubLevel subLevel = Sable.HELPER.getContaining(this.level, this.getBlockPos());
            Vector3d block = JOMLConversion.atCenterOf(this.getBlockPos());
            if (subLevel != null) {
                subLevel.logicalPose().transformPosition(block);
            }
            this.explode(this.level, this.getBlockPos(), block);
        }
    }
    
    public void explode(Level level, BlockPos block, Vector3d blockPos) {
        if (level.isClientSide()) {
            return;
        }
        level.removeBlock(block, false);
        level.explode(null, blockPos.x, blockPos.y, blockPos.z, 5.0f, Level.ExplosionInteraction.MOB);
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
