package com.github.guyapooye.porkchop_express.registry;

import com.google.common.base.Suppliers;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public class PESoundTypes {

    public static final Supplier<SoundType> SWINE = Suppliers.memoize(() -> new SoundType(
            1.0F,
            1.0F,
            PESounds.SWINE_BREAK.get(),
            PESounds.SWINE_STEP.get(),
            PESounds.SWINE_PLACE.get(),
            PESounds.SWINE_HIT.get(),
            PESounds.SWINE_FALL.get()
    ));
}