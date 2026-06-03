package com.github.guyapooye.porkchop_express.content.hold;

import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import dev.ryanhcode.sable.api.physics.constraint.generic.GenericConstraintHandle;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.api.sublevel.SubLevelObserver;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.platform.SableEventPlatform;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.ryanhcode.sable.sublevel.storage.SubLevelRemovalReason;
import dev.ryanhcode.sable.sublevel.system.SubLevelPhysicsSystem;
import foundry.veil.api.network.VeilPacketManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@ParametersAreNonnullByDefault
public class ServerHoldingManager implements SubLevelObserver {
    
    private final Map<SubLevel, List<Player>> holdingPlayers = new WeakHashMap<>();
    private final Map<Player, HoldingPoint> holdingPoints = new WeakHashMap<>();
    
    public void tick() {
        this.holdingPoints.forEach((player, point) -> {
            Vector3d constraintPos = HoldUtil.getConstraintPos(player);
            Quaterniond orientation = new Quaterniond().rotationY(-Mth.DEG_TO_RAD * player.getYRot());
            point.constraint.setFrame2(constraintPos, orientation);
            
            Vector3d blockWorldPos = point.subLevel.logicalPose().transformPosition(JOMLConversion.atCenterOf(point.heldPos));
            if (constraintPos.distanceSquared(blockWorldPos) > 0.25d * 0.5d) {
                SubLevelPhysicsSystem.get(player.level()).getPipeline().teleport(
                        (ServerSubLevel) point.subLevel,
                        constraintPos,
                        orientation
                );
            }
        });
    }
    
    @Override
    public void onSubLevelRemoved(SubLevel subLevel, SubLevelRemovalReason reason) {
        SubLevelObserver.super.onSubLevelRemoved(subLevel, reason);
        List<Player> players = this.holdingPlayers.remove(subLevel);
        if (players != null) {
            for (Player player : players) {
                this.holdingPoints.remove(player);
                this.removeHoldingPoint((ServerPlayer) player);
            }
        }
    }
   
    public HoldingPoint getHeld(ServerPlayer holder) {
        return this.holdingPoints.get(holder);
    }
    
    public void addHoldingPoint(ServerPlayer holder, HoldingPoint point) {
        List<Player> players = this.holdingPlayers.computeIfAbsent(point.subLevel, k -> new ObjectArrayList<>());
        players.add(holder);
        this.holdingPoints.put(holder, point);
        
        VeilPacketManager.player(holder).sendPacket(new HoldBlockPacket(point.heldPos));
    }
    
    public void removeHoldingPoint(ServerPlayer holder) {
        HoldingPoint heldPoint = this.holdingPoints.remove(holder);
        if (heldPoint != null) {
            this.holdingPlayers.get(heldPoint.subLevel).remove(holder);
            heldPoint.constraint.remove();
            
            Vector3d constraintPos = HoldUtil.getConstraintPos(holder);
            Quaterniond orientation = new Quaterniond().rotationY(-Mth.DEG_TO_RAD * holder.getYRot());
            RigidBodyHandle rigidBody = RigidBodyHandle.of((ServerSubLevel) heldPoint.subLevel);
            Vector3d linearVelocity = rigidBody.getLinearVelocity(new Vector3d()).negate();
//            Vec3 playerVelocity = holder.getDeltaMovement();
//            linearVelocity.add(20.0d * playerVelocity.x, 20.0d * playerVelocity.y, 20.0d * playerVelocity.z);

            rigidBody.addLinearAndAngularVelocity(linearVelocity, JOMLConversion.ZERO);
            
            SubLevelPhysicsSystem.get(holder.level()).getPipeline().teleport(
                    (ServerSubLevel) heldPoint.subLevel,
                    constraintPos,
                    orientation
            );
            
            VeilPacketManager.player(holder).sendPacket(new StopHoldBlockPacket());
        }
    }
    
    public static void bootstrap() {
        SableEventPlatform.INSTANCE.onSubLevelContainerReady((level, container) -> {
            if (!level.isClientSide()) {
                container.addObserver(((ServerLevelHoldExtension) level).porkchop_express$getHoldingManager());
            }
        });
    }
    
    public record HoldingPoint(SubLevel subLevel, BlockPos heldPos, GenericConstraintHandle constraint) {
    
    }
    
    
}
