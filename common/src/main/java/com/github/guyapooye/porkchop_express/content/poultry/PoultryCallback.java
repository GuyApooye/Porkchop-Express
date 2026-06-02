package com.github.guyapooye.porkchop_express.content.poultry;

import com.github.guyapooye.porkchop_express.content.poultry.explosive.ExplosivePoultryBlock;
import com.github.guyapooye.porkchop_express.content.poultry.explosive.ExplosivePoultryBlockEntity;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.callback.BlockSubLevelCollisionCallback;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

public class PoultryCallback implements BlockSubLevelCollisionCallback {
    
    public static final PoultryCallback INSTANCE = new PoultryCallback();
    
    public double getTriggerVelocity() {
        return 8.0;
    }
    
    @Override
    public CollisionResult sable$onCollision(final BlockPos pos, final Vector3d hitPos, final double impactVelocity) {
        final SubLevelPhysicsSystem system = SubLevelPhysicsSystem.getCurrentlySteppingSystem();
        final ServerLevel level = system.getLevel();
        
        final BlockState state = level.getBlockState(pos);
        
        if (!(state.getBlock() instanceof PoultryBlock)) {
            return CollisionResult.NONE;
        }
        
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof PoultryBlockEntity blockEntity)) {
            return CollisionResult.NONE;
        }
        
        final double triggerVelocity = this.getTriggerVelocity();
        
        ServerSubLevelContainer container = SubLevelContainer.getContainer(level);
        assert container != null;
        
        ServerSubLevel subLevel = (ServerSubLevel) Sable.HELPER.getContaining(level, pos);
        
        if (impactVelocity * impactVelocity < triggerVelocity * triggerVelocity || subLevel == null) {
            return CollisionResult.NONE;
        }
        
        Pose3d pose = subLevel.logicalPose();
        Vector3d dummy = new Vector3d();
        double distanceToCOM = pose.rotationPoint().distance(JOMLConversion.atCenterOf(pos, dummy));
        
        
        double angularVelocity = RigidBodyHandle.of(subLevel).getAngularVelocity(dummy).length();
        double linearVelocity = RigidBodyHandle.of(subLevel).getLinearVelocity(dummy).length();
        double linearVelocityAtPoint = distanceToCOM * angularVelocity + linearVelocity;
        
        if (linearVelocityAtPoint >= 0.5d * triggerVelocity) {
            return this.doOnCollide(pos, hitPos, impactVelocity, level, blockEntity, subLevel);
        }
        
        return CollisionResult.NONE;
    }
    
    public CollisionResult doOnCollide(
            BlockPos pos,
            Vector3d hitPos,
            double impactVelocity,
            ServerLevel level,
            PoultryBlockEntity blockEntity,
            ServerSubLevel subLevel
    ) {
        blockEntity.despawnSoon();
        return CollisionResult.NONE;
    }
}
