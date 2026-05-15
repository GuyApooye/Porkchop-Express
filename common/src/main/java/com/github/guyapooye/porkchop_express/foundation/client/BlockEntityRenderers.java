package com.github.guyapooye.porkchop_express.foundation.client;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockEntityRenderers {
    
    private static final List<TypeAndRendererPair<?>> BLOCK_ENTITY_RENDERERS = new ObjectArrayList<>();
    
    public static void register(RegisterEvent event) {
        for (TypeAndRendererPair<?> blockEntityRenderer : BLOCK_ENTITY_RENDERERS) {
            register(blockEntityRenderer, event);
        }
    }
    
    private static <T extends BlockEntity> void register(TypeAndRendererPair<T> blockEntityRenderer, RegisterEvent event) {
        event.apply(blockEntityRenderer.type.get(), context -> blockEntityRenderer.rendererProvider.get().apply(context));
    }
    
    public static <T extends BlockEntity> void addRenderer(
            Supplier<? extends BlockEntityType<? extends T>> type,
            Supplier<Function<BlockEntityRendererProvider.Context, BlockEntityRenderer<T>>> renderer
    ) {
        BLOCK_ENTITY_RENDERERS.add(new TypeAndRendererPair<>(type, renderer));
    }
    
    @FunctionalInterface
    public interface RegisterEvent {
        
        <T extends BlockEntity> void apply(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> provider);
        
    }
    
    private record TypeAndRendererPair<T extends BlockEntity>(
            Supplier<? extends BlockEntityType<? extends T>> type,
            Supplier<Function<BlockEntityRendererProvider.Context, BlockEntityRenderer<T>>> rendererProvider
    ) {
    }
    
}
