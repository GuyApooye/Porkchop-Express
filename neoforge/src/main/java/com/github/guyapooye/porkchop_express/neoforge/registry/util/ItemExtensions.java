package com.github.guyapooye.porkchop_express.neoforge.registry.util;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public interface ItemExtensions {
    
    static IClientItemExtensions simpleRenderer(Supplier<? extends BlockEntityWithoutLevelRenderer> customRenderer) {
        return new SimpleRenderer(customRenderer);
    }

    class SimpleRenderer implements IClientItemExtensions {

        private final Supplier<? extends BlockEntityWithoutLevelRenderer> customRenderer;

        public SimpleRenderer(Supplier<? extends BlockEntityWithoutLevelRenderer> customRenderer) {
            this.customRenderer = customRenderer;
        }

        @Override
        @NotNull
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.customRenderer.get();
        }
    }
    
}
