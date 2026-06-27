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
import org.joml.Math;
import org.joml.Vector3d;

public class PoultryCallback implements BlockSubLevelCollisionCallback {
    
    public static final PoultryCallback INSTANCE = new PoultryCallback();
    
    public double getTriggerVelocity() {
        return 8.0;
    }
    
    @Override
    public CollisionResult sable$onCollision(final BlockPos pos1, final BlockPos pos2, final Vector3d hitPos, final double impactVelocity) {
        final SubLevelPhysicsSystem system = SubLevelPhysicsSystem.getCurrentlySteppingSystem();
        final ServerLevel level = system.getLevel();
        
        final BlockState state = level.getBlockState(pos1);
        
        if (!(state.getBlock() instanceof PoultryBlock)) {
            return CollisionResult.NONE;
        }
        
        BlockEntity be = level.getBlockEntity(pos1);
        if (!(be instanceof PoultryBlockEntity blockEntity)) {
            return CollisionResult.NONE;
        }
        
        final double triggerVelocity = this.getTriggerVelocity();
        
        ServerSubLevelContainer container = SubLevelContainer.getContainer(level);
        assert container != null;
        
        ServerSubLevel subLevel = (ServerSubLevel) Sable.HELPER.getContaining(level, pos1);
        
        if (impactVelocity * impactVelocity < triggerVelocity * triggerVelocity || subLevel == null) {
            return CollisionResult.NONE;
        }
        
        Pose3d pose = subLevel.logicalPose();
        Vector3d blockCenter = JOMLConversion.atCenterOf(pos1, new Vector3d());
        double distanceToCOM = pose.rotationPoint().distance(blockCenter);
        
        Vector3d linearVel = new Vector3d();
        double angularVelocity = RigidBodyHandle.of(subLevel).getAngularVelocity(linearVel).length();
        double linearVelocity = RigidBodyHandle.of(subLevel).getLinearVelocity(linearVel).length();
        double linearVelocityAtPoint = Math.fma(distanceToCOM, angularVelocity, linearVelocity);
        
        Vector3d centerToCollisionDir = blockCenter.sub(hitPos, new Vector3d()).normalize();
        double velDot = linearVel.normalize().dot(centerToCollisionDir);
        if (linearVelocityAtPoint >= 0.5d * triggerVelocity && velDot > 0.0) { // velocity and move direction of bird needs to match the collision
            return this.doOnCollide(pos1, pos2, hitPos, impactVelocity, level, blockEntity, subLevel);
        }
        
        return CollisionResult.NONE;
    }
    
    public CollisionResult doOnCollide(
            BlockPos pos1,
            BlockPos pos2,
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
