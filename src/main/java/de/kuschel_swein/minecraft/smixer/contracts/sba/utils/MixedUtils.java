package de.kuschel_swein.minecraft.smixer.contracts.sba.utils;

import codes.biscuit.skyblockaddons.SkyblockAddons;

public interface MixedUtils {

    static MixedUtils getInstance() {
        return (MixedUtils) SkyblockAddons.getInstance().getUtils();
    }

    boolean isInRift();
}
