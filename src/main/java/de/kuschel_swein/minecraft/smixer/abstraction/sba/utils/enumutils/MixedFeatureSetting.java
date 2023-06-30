package de.kuschel_swein.minecraft.smixer.abstraction.sba.utils.enumutils;

import codes.biscuit.skyblockaddons.utils.EnumUtils;

public enum MixedFeatureSetting {

    HEALTH_BAR_HIDE_IN_RIFT,
    HEALTH_TEXT_HIDE_IN_RIFT,
    HEALTH_SHOW_IN_RIFT,
    DEFENCE_TEXT_HIDE_IN_RIFT;


    private EnumUtils.FeatureSetting featureSetting;

    MixedFeatureSetting() {
        for (EnumUtils.FeatureSetting featureSetting : EnumUtils.FeatureSetting.values()) {
            if (featureSetting.name().equals(this.name())) {
                this.featureSetting = featureSetting;

                break;
            }
        }
    }

    public EnumUtils.FeatureSetting getFeatureSetting() {
        return this.featureSetting;
    }
}
