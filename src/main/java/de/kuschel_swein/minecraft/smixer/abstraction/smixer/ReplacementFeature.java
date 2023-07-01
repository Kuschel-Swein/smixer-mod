package de.kuschel_swein.minecraft.smixer.abstraction.smixer;

import codes.biscuit.skyblockaddons.core.Feature;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ReplacementFeature {

    private static final Map<Feature, ReplacementFeature> REPLACEMENT_FEATURES = new HashMap<>();

    static {
        REPLACEMENT_FEATURES.put(Feature.HEALTH_TEXT, ReplacementFeature.soft("neu"));
    }


    private final String modId;

    private ReplacementFeature(String modId) {
        this.modId = modId;
    }

    public static ReplacementFeature soft(String modId) {
        return new ReplacementFeature(modId);
    }

    public static ReplacementFeature hard() {
        return new ReplacementFeature(null);
    }

    @Nullable
    public static ReplacementFeature forFeature(Feature feature) {
        return REPLACEMENT_FEATURES.get(feature);
    }

    public String getModId() {
        return this.modId;
    }

    public boolean isSoft() {
        return !this.isHard();
    }

    public boolean isHard() {
        return (this.modId == null);
    }
}
