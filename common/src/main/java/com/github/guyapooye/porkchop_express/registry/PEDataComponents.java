package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import com.mojang.serialization.Codec;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;

public class PEDataComponents {
    
    public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENT_TYPES = RegistrationProvider.get(
            Registries.DATA_COMPONENT_TYPE, PorkchopExpress.MOD_ID
    );
    
    public static final RegistryObject<DataComponentType<Integer>> ENTITY_HOLDING = DATA_COMPONENT_TYPES.register(
            "entity_holding",
            () -> DataComponentType.<Integer>builder()
                    .networkSynchronized(ByteBufCodecs.INT)
                    .persistent(Codec.INT)
                    .build()
    );
    
    public static void bootstrap() {
    
    }
}
