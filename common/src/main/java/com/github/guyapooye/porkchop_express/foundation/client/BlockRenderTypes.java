package com.github.guyapooye.porkchop_express.foundation.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.Supplier;

public class BlockRenderTypes {
    
    private static final Map<Supplier<? extends Block>, Supplier<RenderType>> BLOCK_RENDER_TYPES = new Object2ObjectArrayMap<>();
    
    public static void registerRenderTypes(RegisterEvent event) {
        BLOCK_RENDER_TYPES.forEach(
                (block, renderType) -> event.apply(block.get(), renderType.get())
        );
    }
    
    public static void addBlockRenderType(Supplier<? extends Block> block, Supplier<RenderType> render) {
        BLOCK_RENDER_TYPES.put(block, render);
    }
    
    @FunctionalInterface
    public interface RegisterEvent {
        void apply(Block block, RenderType renderType);
    }
    
}
