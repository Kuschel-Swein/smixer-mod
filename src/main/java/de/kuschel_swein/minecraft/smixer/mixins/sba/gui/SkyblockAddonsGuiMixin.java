package de.kuschel_swein.minecraft.smixer.mixins.sba.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.SkyblockAddonsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSettings;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import de.kuschel_swein.minecraft.smixer.abstraction.smixer.ReplacementFeature;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.TreeSet;

@Mixin(SkyblockAddonsGui.class)
public abstract class SkyblockAddonsGuiMixin extends GuiScreen {

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

    @Inject(
            method = "addButton",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.BEFORE,
                    ordinal = 3,
                    remap = false
            ),
            remap = false
    )
    private void injectReplacementFeatureToAddButton(
            Feature feature,
            EnumUtils.ButtonType buttonType,
            CallbackInfo callback,
            String text,
            int halfWidth,
            int boxWidth,
            int boxHeight,
            int x,
            double y
    ) {
        ReplacementFeature replacementFeature = ReplacementFeature.forFeature(feature);

        if (replacementFeature != null) {
            // we need the settings button even if there are no settings as we use it as info button
            if (feature.getSettings().isEmpty()) {
                this.buttonList.add(
                        new ButtonSettings(
                                (x + boxWidth - 33),
                                (y + boxHeight - 20),
                                text,
                                SkyblockAddons.getInstance(),
                                feature
                        )
                );
            }
        }

    }
}
