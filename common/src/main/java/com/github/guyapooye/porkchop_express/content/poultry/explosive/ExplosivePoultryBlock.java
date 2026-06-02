package com.github.guyapooye.porkchop_express.content.poultry.explosive;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlock;
import com.github.guyapooye.porkchop_express.registry.PEBlockEntities;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.block.BlockWithSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class ExplosivePoultryBlock extends PoultryBlock implements BlockWithSubLevelCollisionCallback {
    
    public ExplosivePoultryBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public void doSomething(Level level, BlockPos block, Entity entity, Vector3d blockPos, SubLevel subLevel) {
        super.doSomething(level, block, entity, blockPos, subLevel);
        if (level.getBlockEntity(block) instanceof ExplosivePoultryBlockEntity poultryBlockEntity) {
            if (poultryBlockEntity.fuze < 0) {
                poultryBlockEntity.setFuze(5);
            }
        }
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return PEBlockEntities.EXPLOSIVE_POULTRY.get().create(blockPos, blockState);
    }
    
    @Override
    public BlockSubLevelCollisionCallback sable$getCallback() {
        return ExplosivePoultryCallback.INSTANCE;
    }
}
