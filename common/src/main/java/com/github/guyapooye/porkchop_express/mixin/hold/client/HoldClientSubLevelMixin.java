package com.github.guyapooye.porkchop_express.mixin.hold.client;

import com.github.guyapooye.porkchop_express.content.hold.ClientHoldingManager;
import com.github.guyapooye.porkchop_express.content.hold.HoldUtil;
import com.github.guyapooye.porkchop_express.content.swine.WretchedSwineBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.ryanhcode.sable.companion.math.Pose3d;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import dev.ryanhcode.sable.sublevel.ClientSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientSubLevel.class)
public abstract class HoldClientSubLevelMixin extends SubLevel {
    
    protected HoldClientSubLevelMixin(Level level, int plotX, int plotY, Pose3d pose) {
        super(level, plotX, plotY, pose);
    }
    
    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Ldev/ryanhcode/sable/companion/math/Pose3d;set(Ldev/ryanhcode/sable/companion/math/Pose3dc;)Ldev/ryanhcode/sable/companion/math/Pose3d;")
    )
    private Pose3d moveSubLevelToHolding(Pose3d instance, Pose3dc pose, Operation<Pose3d> original) {
        original.call(instance, pose);
        if ((Object) this == ClientHoldingManager.INSTANCE.heldSubLevel.get()) {
            LocalPlayer player = Minecraft.getInstance().player;
            Vector3d position = HoldUtil.getConstraintPos(player);
            instance.position().set(position);
            instance.orientation().rotationY(-Mth.DEG_TO_RAD * player.getYRot());
        }
        return instance;
    }
    
}
