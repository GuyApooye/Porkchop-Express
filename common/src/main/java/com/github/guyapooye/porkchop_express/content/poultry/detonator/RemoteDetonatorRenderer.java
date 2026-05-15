package com.github.guyapooye.porkchop_express.content.poultry.detonator;

import com.github.guyapooye.porkchop_express.content.poultry.PoultryManager;
import com.github.guyapooye.porkchop_express.foundation.client.item.CustomItemRenderer;
import com.github.guyapooye.porkchop_express.registry.PEDataComponents;
import com.github.guyapooye.porkchop_express.registry.PEPartialModels;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RemoteDetonatorRenderer extends CustomItemRenderer {
    
    private static final RemoteDetonatorRenderer INSTANCE = new RemoteDetonatorRenderer();
    
    @Override
    protected void render(
            ItemStack itemStack,
            BakedModel model,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        Integer entityId = itemStack.get(PEDataComponents.ENTITY_HOLDING);
        
        float cooldownPercent = 1.0f;
        boolean hasBirdsNearby = false;
        if (entityId != null) {
            Entity entity = minecraft.level.getEntity(entityId);
            float partialTick = minecraft.getTimer().getGameTimeDeltaPartialTick(false);
            if (entity instanceof Player player) {
                Item item = itemStack.getItem();
                cooldownPercent = 1.0f - player.getCooldowns().getCooldownPercent(item, partialTick);
                
                final float begin = 0.1f;
                if (cooldownPercent < begin) {
                    cooldownPercent = 1.0f - cooldownPercent / begin;
                } else {
                    cooldownPercent = (cooldownPercent - begin) / (1.0f - begin);
                }
            }
            if (entity != null) {
                if (entity instanceof LivingEntity livingEntity) {
                    hasBirdsNearby = livingEntity.getMainHandItem().equals(itemStack) || livingEntity.getOffhandItem().equals(itemStack);
                }
                if (hasBirdsNearby) {
                    hasBirdsNearby = PoultryManager.hasBirdsNear(
                            minecraft.level,
                            JOMLConversion.toJOML(entity.getEyePosition(partialTick)),
                            JOMLConversion.toJOML(entity.getViewVector(partialTick)),
                            PoultryManager.BIAS,
                            partialTick
                    );
                }
            }
            
        }
        
        BakedModel mainModel;
        BakedModel buttonModel;
        poseStack.pushPose();
        if (hasBirdsNearby) {
            mainModel = PEPartialModels.DETONATOR_MAIN_GREEN.get();
            buttonModel = PEPartialModels.DETONATOR_BUTTON_GREEN.get();
        } else {
            mainModel = PEPartialModels.DETONATOR_MAIN_RED.get();
            buttonModel = PEPartialModels.DETONATOR_BUTTON_RED.get();
        }
        renderModelAndFoil(itemStack, mainModel, RenderType.cutout(), poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.translate(0.0f, 0.0f, Math.max((1.0f-cooldownPercent)/16.0f, -0.25f));
        renderModelAndFoil(itemStack, buttonModel, RenderType.cutout(), poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }
    
    public static RemoteDetonatorRenderer get() {
        return INSTANCE;
    }
}
