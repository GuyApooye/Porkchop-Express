package com.github.guyapooye.porkchop_express.mixin.hold.client;

import com.github.guyapooye.porkchop_express.content.hold.ClientHoldingManager;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemInHandRenderer.class, priority = 1100)
public class HoldItemInHandRenderer {
    
    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private static void renderBothHandsWhenHolding(
            AbstractClientPlayer pPlayer,
            float pPartialTicks,
            float pPitch,
            InteractionHand pHand,
            float pSwingProgress,
            ItemStack pStack,
            float pEquippedProgress,
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pCombinedLight,
            CallbackInfo ci,
            @Local(name = "flag") LocalBooleanRef flag
    ) {
        if (ClientHoldingManager.isHolding()) {
            flag.set(true);
        }
    }
    
}
