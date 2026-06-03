package com.github.guyapooye.porkchop_express.mixin.hold;

import com.github.guyapooye.porkchop_express.content.hold.ClientHoldingManager;
import com.github.guyapooye.porkchop_express.content.hold.ServerHoldingManager;
import com.github.guyapooye.porkchop_express.ext.hold.ServerLevelHoldExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import dev.ryanhcode.sable.ActiveSableCompanion;
import dev.ryanhcode.sable.api.math.LevelReusedVectors;
import dev.ryanhcode.sable.sublevel.SubLevel;
import dev.ryanhcode.sable.sublevel.entity_collision.SubLevelEntityCollision;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SubLevelEntityCollision.class, priority = 1100)
public class HoldSubLevelEntityCollisionMixin {
    
    @WrapOperation(method = "collide", at = @At(value = "INVOKE", target = "Ldev/ryanhcode/sable/ActiveSableCompanion;getTrackingSubLevel(Lnet/minecraft/world/entity/Entity;)Ldev/ryanhcode/sable/sublevel/SubLevel;", ordinal = 0))
    private static SubLevel removeCollisionFromHeldSubLevel0(
            ActiveSableCompanion instance,
            Entity entity,
            Operation<SubLevel> original
    ) {
        SubLevel subLevel = original.call(instance, entity);
        ServerPlayer serverPlayer = (ServerPlayer) entity;
        ServerHoldingManager manager = ((ServerLevelHoldExtension) serverPlayer.serverLevel()).porkchop_express$getHoldingManager();
        ServerHoldingManager.HoldingPoint holdingPoint = manager.getHeld(serverPlayer);
        if (holdingPoint != null && holdingPoint.subLevel() == subLevel) {
            subLevel = null;
        }
        return subLevel;
    }
    
    @Inject(method = "collide", at = @At("HEAD"))
    private static void isLocalPLayer(
            Entity entity,
            Vec3 collisionMotionMoj,
            Vec3 velocityMotionMoj,
            LevelReusedVectors sink,
            CallbackInfoReturnable<SubLevelEntityCollision.CollisionInfo> cir,
            @Share("isLocalPLayer") LocalBooleanRef isLocalPlayer
    ) {
        isLocalPlayer.set(false);
        if (entity instanceof Player player) {
            isLocalPlayer.set(player.isLocalPlayer());
        }
    }
    
    @WrapOperation(method = "collide", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectSet;add(Ljava/lang/Object;)Z"))
    private static boolean removeCollisionFromHeldSubLevel1(ObjectSet instance, Object o, Operation<Boolean> original, @Local(argsOnly = true) Entity entity, @Share("isLocalPLayer") LocalBooleanRef isLocalPlayer) {
        if (isLocalPlayer.get() && o == ClientHoldingManager.INSTANCE.heldSubLevel.get()) {
            return false;
        }
        return original.call(instance, o);
    }
}
