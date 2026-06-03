package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.content.swine.WretchedSwineBlockEntity;
import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.physics.PhysicsPipeline;
import dev.ryanhcode.sable.api.physics.constraint.ConstraintJointAxis;
import dev.ryanhcode.sable.api.physics.constraint.generic.GenericConstraintConfiguration;
import dev.ryanhcode.sable.api.physics.constraint.generic.GenericConstraintHandle;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
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
import org.joml.Quaterniond;
import org.joml.Vector2d;
import org.joml.Vector3d;

public class HoldUtil {
    
    public static void hold(Player holder, BlockPos blockPos, Level level) {
        if (!level.isClientSide()) {
            ServerSubLevel subLevel = (ServerSubLevel) Sable.HELPER.getContaining(level, blockPos);
            ServerHoldingManager serverManager = ((ServerLevelHoldExtension) subLevel.getLevel()).porkchop_express$getHoldingManager();
            ServerHoldingManager.HoldingPoint held = serverManager.getHeld((ServerPlayer) holder);
            if (held != null) {
                serverManager.removeHoldingPoint((ServerPlayer) holder);
                return;
            }
            
            Vector3d centerPos = JOMLConversion.atCenterOf(blockPos);
            
            if (subLevel.logicalPose().transformPosition(centerPos, new Vector3d()).y < (holder.getY() + 0.45)) {
                return;
            }
            
            Vector3d constraintPos = getConstraintPos(holder);
            GenericConstraintConfiguration configuration = new GenericConstraintConfiguration(
                    centerPos,
                    constraintPos,
                    new Quaterniond(),
                    new Quaterniond()
            );
            PhysicsPipeline pipeline = SubLevelPhysicsSystem.get(level).getPipeline();
            Quaterniond orientation = new Quaterniond().rotationY(-Mth.DEG_TO_RAD * holder.getYRot());
            pipeline.teleport(subLevel, constraintPos, orientation);
            
            GenericConstraintHandle handle = pipeline.addConstraint(subLevel, null, configuration);
            
            for (ConstraintJointAxis axis : ConstraintJointAxis.ALL) {
                handle.setMotor(axis, 0.0d, 10000.0d, 100.0d, false, 0.0d);
            }
            
            ((ServerLevelHoldExtension) subLevel.getLevel()).porkchop_express$getHoldingManager()
                    .addHoldingPoint((ServerPlayer) holder, new ServerHoldingManager.HoldingPoint(subLevel, blockPos, handle));
            
        }
    }
    
    @NotNull
    public static Vector3d getConstraintPos(Player player) {
        final double holdHeight = 1.0d;
        
        Vec3 lookAngle = player.getLookAngle();
        Vector3d constraintPos = JOMLConversion.toJOML(player.position());
        Vector2d lookVector = new Vector2d(lookAngle.x, lookAngle.z);
        lookVector.normalize(1.5d);
        constraintPos.add(lookVector.x, holdHeight, lookVector.y);
        return constraintPos;
    }
    
    @NotNull
    public static Vector3d getConstraintPos(Player player, float partialTick) {
        Vec3 lookAngle = player.getViewVector(partialTick);
        Vector3d constraintPos = JOMLConversion.toJOML(player.position());
        Vector2d lookVector = new Vector2d(lookAngle.x, lookAngle.z);
        lookVector.normalize(1.6d);
        constraintPos.add(lookVector.x, 1.0d, lookVector.y);
        return constraintPos;
    }
    
}
