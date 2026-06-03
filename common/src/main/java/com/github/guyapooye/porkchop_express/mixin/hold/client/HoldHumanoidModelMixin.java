package com.github.guyapooye.porkchop_express.mixin.hold.client;

import com.github.guyapooye.porkchop_express.content.hold.ClientHoldingManager;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HumanoidModel.class)
public class HoldHumanoidModelMixin<T extends LivingEntity> {
    
    @Shadow
    @Final
    public ModelPart rightArm;
    
    @Shadow
    @Final
    public ModelPart leftArm;
    
    @Shadow
    @Final
    public ModelPart body;
    
    @Shadow
    @Final
    public ModelPart head;
    
    @WrapMethod(method = {"poseRightArm", "poseLeftArm"})
    private void poseArmsHold(T pLivingEntity, Operation<Void> original) {
        if (pLivingEntity instanceof LocalPlayer localPlayer) {
            if (ClientHoldingManager.INSTANCE.heldSubLevel.get() != null) {
                this.rightArm.xRot = 0.125f * this.rightArm.xRot - 0.5f;
                this.leftArm.xRot = 0.125f * this.leftArm.xRot - 0.5f;
                
                this.rightArm.yRot += 0.0625f * Mth.HALF_PI;
                this.leftArm.yRot -= 0.0625f * Mth.HALF_PI;
                return;
            }
        }
        original.call(pLivingEntity);
    }

}
