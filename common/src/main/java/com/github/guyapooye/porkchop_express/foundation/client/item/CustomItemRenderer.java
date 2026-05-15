package com.github.guyapooye.porkchop_express.foundation.client.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class CustomItemRenderer extends BlockEntityWithoutLevelRenderer {
    
    private static final Direction[] directions = Direction.values();
    
    protected static float[] color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    
    public CustomItemRenderer() {
        super(null, null);
    }
    
    @Override
    public void renderByItem(
            ItemStack itemStack,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        
        BakedModel model = Minecraft.getInstance()
                .getItemRenderer()
                .getModel(itemStack, null, null, 42);
        
        this.render(itemStack, model, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
    }
    
    protected abstract void render(
            ItemStack itemStack,
            BakedModel model,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    );
    
    protected static void renderModelAndFoil(
            ItemStack itemStack,
            BakedModel model,
            RenderType renderType,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        
        PoseStack.Pose pose = poseStack.last();
        RandomSource random = RandomSource.create(42L);
        
        if (itemStack.hasFoil()) {
            renderPassFoil(pose, model, bufferSource, renderType, random, packedLight, packedOverlay);
        } else {
            renderPass(pose, model, bufferSource, renderType, random, packedLight, packedOverlay);
        }
        
    }
    
    protected static void renderPass(
            PoseStack.Pose pose,
            BakedModel pass,
            MultiBufferSource bufferSource,
            RenderType renderType,
            RandomSource random,
            int packedLight,
            int packedOverlay
    ) {
        VertexConsumer builder = bufferSource.getBuffer(renderType);
        renderPass(pose, pass, builder, random, packedLight, packedOverlay);
    }
    
    protected static void renderPassFoil(
            PoseStack.Pose pose,
            BakedModel pass,
            MultiBufferSource bufferSource,
            RenderType renderType,
            RandomSource random,
            int packedLight,
            int packedOverlay
    ) {
        VertexConsumer builder = ItemRenderer.getFoilBuffer(bufferSource, renderType, true, true);
        renderPass(pose, pass, builder, random, packedLight, packedOverlay);
    }
    
    protected static void renderPass(
            PoseStack.Pose pose,
            BakedModel pass,
            VertexConsumer builder,
            RandomSource random,
            int packedLight,
            int packedOverlay
    ) {
        for (Direction side : directions) {
            renderQuads(pose, pass, side, random, builder, packedLight, packedOverlay);
        }
        
        renderQuads(pose, pass, null, random, builder, packedLight, packedOverlay);
    }
    
    protected static void renderQuads(
            PoseStack.Pose pose,
            BakedModel pass,
            Direction side,
            RandomSource random,
            VertexConsumer builder,
            int packedLight,
            int packedOverlay
    ) {
        List<BakedQuad> quads = pass.getQuads(null, side, random);
        for (int j = 0; j < quads.size(); j++) {
            BakedQuad quad = quads.get(j);
            builder.putBulkData(pose, quad, color[0], color[1], color[2], color[3], packedLight, packedOverlay);
        }
    }
    
}
