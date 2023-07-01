package de.kuschel_swein.minecraft.smixer.abstraction.smixer;

import codes.biscuit.skyblockaddons.core.Feature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReplacementFeature {

    private static final Map<Feature, ReplacementFeature> REPLACEMENT_FEATURES = new HashMap<>();

    static {
        REPLACEMENT_FEATURES.put(Feature.FANCY_WARP_MENU, ReplacementFeature.soft("FancyWarpMenu"));

        // replaced by NEU
        REPLACEMENT_FEATURES.put(Feature.DUNGEONS_MAP_DISPLAY, ReplacementFeature.soft("NotEnoughUpdates"));
        REPLACEMENT_FEATURES.put(Feature.LOCK_SLOTS, ReplacementFeature.soft("NotEnoughUpdates"));
        REPLACEMENT_FEATURES.put(Feature.SHOW_BACKPACK_PREVIEW, ReplacementFeature.soft("NotEnoughUpdates"));
    }


    @Nullable
    private final String modName;

    @Nullable
    private final String modId;

    private ReplacementFeature(@Nullable String modName, @Nullable String modId) {
        this.modName = modName;
        this.modId = modId;
    }

    public static ReplacementFeature soft(@NotNull String modName, @NotNull String modId) {
        return new ReplacementFeature(modName, modId);
    }

    public static ReplacementFeature soft(@NotNull String modName) {
        return new ReplacementFeature(modName, modName.toLowerCase(Locale.US));
    }

    public static ReplacementFeature hard() {
        return new ReplacementFeature(null, null);
    }

    @Nullable
    public static ReplacementFeature forFeature(@NotNull Feature feature) {
        return REPLACEMENT_FEATURES.get(feature);
    }

    @Nullable
    public String getModName() {
        return this.modName;
    }

    @Nullable
    public String getModId() {
        return this.modId;
    }

    public boolean isSoft() {
        return !this.isHard();
    }

    public boolean isHard() {
        return (this.modName == null || this.modId == null);
    }
}
