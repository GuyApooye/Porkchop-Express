package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

public class PEPartialModels {

    public static final PartialModel
            DETONATOR_MAIN_RED = item("remote_detonator/main_red"),
            DETONATOR_MAIN_GREEN = item("remote_detonator/main_green"),
            DETONATOR_BUTTON_RED = item("remote_detonator/button_red"),
            DETONATOR_BUTTON_GREEN = item("remote_detonator/button_green")
    ;

    private PEPartialModels() {
    }

    private static PartialModel block(String path) {
        return PartialModel.of(ResourceLocation.fromNamespaceAndPath(PorkchopExpress.MOD_ID, "block/" + path));
    }

    private static PartialModel item(String path) {
        return PartialModel.of(ResourceLocation.fromNamespaceAndPath(PorkchopExpress.MOD_ID, "item/" + path));
    }

    @ApiStatus.Internal
    public static void bootstrap() {
    }
}