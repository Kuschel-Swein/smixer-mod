package de.kuschel_swein.minecraft.smixer.mixins.sba.gui;

import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import de.kuschel_swein.minecraft.smixer.abstraction.smixer.ReplacementFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.TreeSet;

@Mixin(SkyblockAddonsGui.class)
public class SkyblockAddonsGuiMixin {

    @Redirect(
            method = "initGui",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/TreeSet;add(Ljava/lang/Object;)Z",
                    ordinal = 2,
                    remap = false
            )
    )
    private boolean redirectReplacementFeatureCheckFromRemoteFeaturesAdding(
            TreeSet<Feature> features,
            Object featureObject
    ) {
        if (featureObject instanceof Feature) {
            Feature feature = (Feature) featureObject;
            ReplacementFeature replacementFeature = ReplacementFeature.forFeature(feature);

            if (replacementFeature == null || replacementFeature.isSoft()) {
                return features.add(feature);
            }
        }

        return false;
    }
}
