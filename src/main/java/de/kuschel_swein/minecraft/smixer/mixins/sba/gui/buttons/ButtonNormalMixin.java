package de.kuschel_swein.minecraft.smixer.mixins.sba.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonNormal;
import de.kuschel_swein.minecraft.smixer.abstraction.smixer.ReplacementFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;

@Mixin(ButtonNormal.class)
public class ButtonNormalMixin {

    @Unique
    private ReplacementFeature replacementFeature;

    @Inject(
            method = "<init>(DDIILjava/lang/String;Lcodes/biscuit/skyblockaddons/SkyblockAddons;Lcodes/biscuit/skyblockaddons/core/Feature;)V",
            at = @At("TAIL"),
            remap = false
    )
    private void injectReplacementFeatureCheckToInit(
            double x,
            double y,
            int width,
            int height,
            String buttonText,
            SkyblockAddons main,
            Feature feature,
            CallbackInfo callback
    ) {
        this.replacementFeature = ReplacementFeature.forFeature(feature);
    }

    @ModifyArgs(
            method = "drawButton",
            at = @At(
                    value = "INVOKE",
                    target = "Lcodes/biscuit/skyblockaddons/gui/buttons/ButtonNormal;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V",
                    remap = false
            )
    )
    private void injectReplacementFeatureTextToDrawButton(Args args) {
        int colorValue = args.get(4);

        if (this.replacementFeature != null) {
            // it's way easier to manually calculate the alpha value instead of capturing the locals
            int alpha = (colorValue >> 24) & 0xff;
            String newText = Translations.getMessage("smixer.settings.messages.replacement.replaced");
            Color newColor = new Color(222, 68, 76, alpha);

            args.set(1, newText);
            args.set(4, newColor.getRGB());
        }
    }
}
