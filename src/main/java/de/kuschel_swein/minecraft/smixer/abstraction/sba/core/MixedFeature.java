package de.kuschel_swein.minecraft.smixer.abstraction.sba.core;

import codes.biscuit.skyblockaddons.core.Feature;

public enum MixedFeature {

    HEALTH_BAR_HIDE_IN_RIFT(501),
    HEALTH_TEXT_HIDE_IN_RIFT(502);

    public final Feature feature;

    MixedFeature(int featureId) {
        this.feature = Feature.fromId(featureId);
    }

    public boolean isEnabled() {
        return this.feature.isEnabled();
    }
}
