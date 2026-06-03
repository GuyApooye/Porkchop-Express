package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.content.swine.WretchedSwineBlockEntity;
import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.PhysicsPipeline;
import dev.ryanhcode.sable.api.physics.constraint.ConstraintJointAxis;
import dev.ryanhcode.sable.api.physics.constraint.generic.GenericConstraintConfiguration;
import dev.ryanhcode.sable.api.physics.constraint.generic.GenericConstraintHandle;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.mixinterface.entity.entity_sublevel_collision.EntityMovementExtension;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import foundry.veil.api.network.VeilPacketManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.*;

public class HoldUtil {
    
    public static void hold(Player holder, BlockPos blockPos, Level level) {
        if (!level.isClientSide()) {
            ServerSubLevel subLevel = (ServerSubLevel) Sable.HELPER.getContaining(level, blockPos);
            if (subLevel == null) {
                return;
            }
            
            ServerHoldingManager serverManager = ((ServerLevelHoldExtension) subLevel.getLevel()).porkchop_express$getHoldingManager();
            ServerHoldingManager.HoldingPoint held = serverManager.getHeld((ServerPlayer) holder);
            Vector3d constraintPos = getConstraintPos(holder);
            if (held != null) {
                tryDrop((ServerPlayer) holder, blockPos, level, subLevel, serverManager);
                return;
            }
            
            Vector3d centerPos = JOMLConversion.atCenterOf(blockPos);
            
            if (subLevel.logicalPose().transformPosition(centerPos, new Vector3d()).distanceSquared(constraintPos) > 1.5d * 1.5d || ((EntityMovementExtension) holder).sable$getTrackingSubLevel() == subLevel) {
                return; // nuh uh uh
            }
            
            PhysicsPipeline pipeline = SubLevelPhysicsSystem.get(level).getPipeline();
            Quaterniond orientation = new Quaterniond().rotationY(-Mth.DEG_TO_RAD * holder.getYRot());
            
            Vector3d teleportPos = matchConstraintPos(subLevel.logicalPose(), blockPos, constraintPos, new Vector3d());
            
            double ctl = constraintPos.lengthSquared();
            double tpl = teleportPos.lengthSquared();
            
            if (Double.isNaN(ctl) || Double.isInfinite(ctl) || Double.isNaN(tpl) || Double.isInfinite(tpl)) {
                return;
            }
            
            pipeline.teleport(subLevel, teleportPos, orientation);
            
            GenericConstraintConfiguration configuration = new GenericConstraintConfiguration(
                    centerPos,
                    constraintPos,
                    new Quaterniond(),
                    new Quaterniond()
            );
            GenericConstraintHandle handle = pipeline.addConstraint(subLevel, null, configuration);
            
            for (ConstraintJointAxis axis : ConstraintJointAxis.ALL) {
                handle.setMotor(axis, 0.0d, 10000.0d, 100.0d, false, 0.0d);
            }
            
            ((ServerLevelHoldExtension) subLevel.getLevel()).porkchop_express$getHoldingManager()
                    .addHoldingPoint((ServerPlayer) holder, new ServerHoldingManager.HoldingPoint(subLevel, blockPos, handle));
            
        }
    }
    
    public static void tryDrop(
            ServerPlayer holder,
            BlockPos blockPos,
            Level level,
            ServerSubLevel subLevel,
            ServerHoldingManager serverManager
    ) {
        if (Boolean.TRUE.equals(Sable.HELPER.runIncludingSubLevels(
                level,
                blockPos.getCenter(),
                false, subLevel,
                (subLevel0, pos) -> level.getBlockState(pos).canBeReplaced() ? null : Boolean.TRUE
        ))) {
            return;
        }
        
        serverManager.removeHoldingPoint(holder);
    }
    
    @NotNull
    public static Vector3d getConstraintPos(Player player) {
        final double holdHeight = 0.75d;
        
        Vec3 lookAngle = player.getLookAngle();
        Vector3d constraintPos = JOMLConversion.toJOML(player.position());
        Vector2d lookVector = new Vector2d(lookAngle.x, lookAngle.z);
        lookVector.normalize(0.9d);
        constraintPos.add(lookVector.x, holdHeight, lookVector.y);
        return constraintPos;
    }
    
    @NotNull
    public static Vector3d getConstraintPos(Player player, float partialTick) {
        final double holdHeight = 0.75d;
        
        Vec3 lookAngle = player.getViewVector(partialTick);
        Vector3d constraintPos = JOMLConversion.toJOML(player.getPosition(partialTick));
        Vector2d lookVector = new Vector2d(lookAngle.x, lookAngle.z);
        lookVector.normalize(0.9d);
        constraintPos.add(lookVector.x, holdHeight, lookVector.y);
        return constraintPos;
    }
    
    public static Vector3d matchConstraintPos(Pose3d pose, BlockPos held, Vector3dc constraintPos, Vector3d dest) {
        Vector3d heldPos = JOMLConversion.atCenterOf(held);
        Vector3d toConstraint = pose.transformPosition(heldPos, new Vector3d());
        constraintPos.sub(toConstraint, toConstraint);
        
        return pose.position().add(toConstraint, dest);
    }
    
    public static Pose3d matchConstraint(Pose3d pose, BlockPos held, Vector3dc constraintPos, Quaterniondc constraintOrientation) {
        pose.orientation().set(constraintOrientation);
        matchConstraintPos(pose, held, constraintPos, pose.position());
        return pose;
    }
    
}
