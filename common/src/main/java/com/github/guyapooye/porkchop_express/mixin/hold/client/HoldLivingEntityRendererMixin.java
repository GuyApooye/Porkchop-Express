package com.github.guyapooye.porkchop_express.mixin.hold.client;

import com.github.guyapooye.porkchop_express.content.hold.ClientHoldingManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LivingEntityRenderer.class, priority = 1100)
public class HoldLivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @WrapOperation(
            method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F", ordinal = 0)
    )
    private float holdingBodyRot(float delta, float start, float end, Operation<Float> original, @Local(argsOnly = true) T pEntity) {
        if (pEntity instanceof AbstractClientPlayer player) {
            if (ClientHoldingManager.isHolding(player)) {
                start = pEntity.yHeadRotO;
                end = pEntity.yHeadRot;
            }
        }
        
        return original.call(delta, start, end);
    }

}
