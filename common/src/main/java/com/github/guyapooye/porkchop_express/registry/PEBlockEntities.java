package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.github.guyapooye.porkchop_express.content.poultry.PoultryBlockEntity;
import com.github.guyapooye.porkchop_express.content.poultry.explosive.ExplosivePoultryBlockEntity;
import com.github.guyapooye.porkchop_express.content.poultry.explosive.ExplosivePoultryRenderer;
import com.github.guyapooye.porkchop_express.content.poultry.splitting.SplittingPoultryBlockEntity;
import com.github.guyapooye.porkchop_express.content.swine.WretchedSwineBlockEntity;
import com.github.guyapooye.porkchop_express.registry.util.BlockEntityBuilder;
import com.github.guyapooye.porkchop_express.registry.util.TriFunction;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PEBlockEntities {
    
    public static final RegistrationProvider<BlockEntityType<?>> BLOCKS_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, PorkchopExpress.MOD_ID);
    public static final Registry<BlockEntityType<?>> REGISTRY = BLOCKS_ENTITY_TYPES.asVanillaRegistry();
    
    public static void bootstrap() {
    }
    
    public static final RegistryObject<BlockEntityType<WretchedSwineBlockEntity>> SWINE = blockEntity("wretched_swine", WretchedSwineBlockEntity::new)
            .validBlocks(PEBlocks.SWINE)
            .build();
    
    public static final RegistryObject<BlockEntityType<PoultryBlockEntity>> POULTRY = blockEntity("furious_poultry", PoultryBlockEntity::new)
            .validBlocks(PEBlocks.RED_POULTRY)
            .build();

    public static final RegistryObject<BlockEntityType<ExplosivePoultryBlockEntity>> EXPLOSIVE_POULTRY = blockEntity("explosive_poultry", ExplosivePoultryBlockEntity::new)
            .validBlocks(PEBlocks.EXPLOSIVE_POULTRY)
            .renderer(() -> ExplosivePoultryRenderer::new)
            .build();

    public static final RegistryObject<BlockEntityType<SplittingPoultryBlockEntity>> SPLITTING_POULTRY = blockEntity("splitting_poultry", SplittingPoultryBlockEntity::new)
            .validBlocks(PEBlocks.SPLITTING_POULTRY)
            .build();

    private static <BE extends BlockEntity> BlockEntityBuilder<BE> blockEntity(String name, TriFunction<BlockEntityType<BE>, BlockPos, BlockState, BE> factory) {
        return new BlockEntityBuilder<>(name, factory);
    }
    
}