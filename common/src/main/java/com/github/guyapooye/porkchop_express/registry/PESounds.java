package com.github.guyapooye.porkchop_express.registry;

import com.github.guyapooye.porkchop_express.PorkchopExpress;
import foundry.veil.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

import java.util.function.Supplier;

public class PESounds {

    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, PorkchopExpress.MOD_ID);
    
    public static final Supplier<SoundEvent> SWINE_BREAK =
            register("swine_break");

    public static final Supplier<SoundEvent> SWINE_STEP =
            register("swine_step");

    public static final Supplier<SoundEvent> SWINE_PLACE =
            register("swine_place");

    public static final Supplier<SoundEvent> SWINE_HIT =
            register("swine_hit");

    public static final Supplier<SoundEvent> SWINE_FALL =
            register("swine_fall");

    public static final Supplier<SoundEvent> MONCH =
            register("monch");
    
    public static final Supplier<SoundEvent> POULTRY_ACTIVATE =
            register("poultry_activate");

    public static final Supplier<SoundEvent> WRETCHED_DISC = register("wretched_disc");
    public static final ResourceKey<JukeboxSong> WRETCHED_DISC_KEY = createSong();

    private static ResourceKey<JukeboxSong> createSong() {
        return ResourceKey.create(Registries.JUKEBOX_SONG, PorkchopExpress.id("wretched_disc"));
    }

    private static Supplier<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(PorkchopExpress.id(name)));
    }
    
    public static void bootstrap() {
    }
}