package com.github.guyapooye.porkchop_express.content.swine;

import com.github.guyapooye.porkchop_express.registry.PESoundTypes;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class WretchedSwineCallback implements BlockSubLevelCollisionCallback {
    
    public static final WretchedSwineCallback INSTANCE = new WretchedSwineCallback();
    
    @Override
    public CollisionResult sable$onCollision(final BlockPos pos1, final BlockPos pos2, final Vector3d hitPos, final double impactVelocity) {
        final SubLevelPhysicsSystem system = SubLevelPhysicsSystem.getCurrentlySteppingSystem();
        final ServerLevel level = system.getLevel();
        
        final BlockState state = level.getBlockState(pos1);
        
        if (!(state.getBlock() instanceof WretchedSwineBlock)) {
            return CollisionResult.NONE;
        }
        
        if (!(level.getBlockEntity(pos1) instanceof WretchedSwineBlockEntity blockEntity)) {
            return CollisionResult.NONE;
        }
        
        
        final double triggerVelocity = 8.0d;
        final double excessiveTriggerVelocity = 16.0d;
        
        if (impactVelocity * impactVelocity >= excessiveTriggerVelocity * excessiveTriggerVelocity) {
            float volume = 0.5f;
            float pitch = level.random.nextFloat() * 0.1f;
            SoundType soundType = PESoundTypes.SWINE.get();
            SoundEvent sound = soundType.getPlaceSound();
            level.destroyBlock(pos1, false);
            level.playSound(null, hitPos.x, hitPos.y, hitPos.z, sound, SoundSource.BLOCKS, volume, pitch);
            
            return new CollisionResult(JOMLConversion.ZERO, true);
        } else if (impactVelocity * impactVelocity >= triggerVelocity * triggerVelocity) {
            return this.hurt(level, pos1, state, hitPos, blockEntity);
        }
        
        return CollisionResult.NONE;
    }
    
    public CollisionResult hurt(ServerLevel level, BlockPos pos, BlockState state, Vector3d hitPos, WretchedSwineBlockEntity blockEntity) {
        
        if (!blockEntity.shouldApplyCollision()) {
            return CollisionResult.NONE;
        }
        blockEntity.setCollisionCooldown(10);
        
        WretchedSwineBlock.Mood mood = state.getValue(WretchedSwineBlock.MOOD);
        BlockState newState;
        
        float volume = 0.4f;
        float pitch = level.random.nextFloat();
        SoundType soundType = PESoundTypes.SWINE.get();
        SoundEvent sound = soundType.getPlaceSound();
        
        switch (mood) {
            case HAPPY -> {
                newState = state.setValue(WretchedSwineBlock.MOOD, WretchedSwineBlock.Mood.HURT);
                blockEntity.replaceNextTick(newState);
                
                volume *= 3.0f;
                pitch *= 0.4f;
                pitch += 1.6f;
            }
            case HURT -> {
                level.destroyBlock(pos, false);
                volume *= 0.4f;
                pitch *= 0.15f;
            }
        }
        
        level.playSound(null, hitPos.x, hitPos.y, hitPos.z, sound, SoundSource.BLOCKS, volume, pitch);
        
        return CollisionResult.NONE;
    }
}
