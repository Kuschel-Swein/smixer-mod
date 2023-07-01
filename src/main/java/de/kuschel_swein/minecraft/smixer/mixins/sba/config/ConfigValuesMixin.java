package de.kuschel_swein.minecraft.smixer.mixins.sba.config;

import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Feature;
import de.kuschel_swein.minecraft.smixer.abstraction.smixer.ReplacementFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConfigValues.class)
public class ConfigValuesMixin {

    @Inject(
            method = "isRemoteDisabled",
            at = @At(
                    value = "INVOKE",
                    target = "Lcodes/biscuit/skyblockaddons/core/OnlineData;getDisabledFeatures()Ljava/util/HashMap;",
                    shift = At.Shift.BY,
                    by = -2,
                    remap = false
            ),
            cancellable = true,
            remap = false
    )
    private void injectReplacementFeatureCheckToIsRemoveDisabled(
            Feature feature,
            CallbackInfoReturnable<Boolean> callback
    ) {

        if (ReplacementFeature.forFeature(feature) != null) {
            callback.setReturnValue(true);
        }
    }
}
