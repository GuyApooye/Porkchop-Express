package com.github.guyapooye.porkchop_express.neoforge.registry;

import com.github.guyapooye.porkchop_express.registry.util.BlockBuilder;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public final class NeoForgePEBlocks {
    
    public static final RegistryObject<Block> JESUS = block("jesus", Block::new)
            .properties(p -> p
                    .noOcclusion()
                    .strength(0.2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.NETHERITE_BLOCK))
            .lang("Jesus")
            .dropSelf()
            .simpleItem()
            .build();
    
    private static <B extends Block> BlockBuilder<B> block(String name, Function<BlockBehaviour.Properties, B> factory) {
        return new BlockBuilder<>(name, factory);
    }
    
    public static void bootstrap() {
    
    }
    
    private NeoForgePEBlocks() {
    }
    
}
