package de.kuschel_swein.minecraft.smixer.abstraction.smixer;

import codes.biscuit.skyblockaddons.core.Feature;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ReplacementFeature {

    private static final Map<Feature, ReplacementFeature> REPLACEMENT_FEATURES = new HashMap<>();

    static {
        REPLACEMENT_FEATURES.put(Feature.HEALTH_TEXT, ReplacementFeature.hard());
    }


    private final String modId;

    private final String modUrl;

    private ReplacementFeature(String modId, String modUrl) {
        this.modId = modId;
        this.modUrl = modUrl;
    }

    public static ReplacementFeature soft(String modId, String modUrl) {
        return new ReplacementFeature(modId, modUrl);
    }

    public static ReplacementFeature hard() {
        return new ReplacementFeature(null, null);
    }

    @Nullable
    public static ReplacementFeature forFeature(Feature feature) {
        return REPLACEMENT_FEATURES.get(feature);
    }

    public String getModId() {
        return this.modId;
    }

    public String getModUrl() {
        return this.modUrl;
    }

    public boolean isSoft() {
        return !this.isHard();
    }

    public boolean isHard() {
        return (this.modId == null);
    }
}
