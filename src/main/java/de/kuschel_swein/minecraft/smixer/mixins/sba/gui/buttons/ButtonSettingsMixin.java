package de.kuschel_swein.minecraft.smixer.mixins.sba.gui.buttons;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonSettings;
import de.kuschel_swein.minecraft.smixer.abstraction.smixer.ReplacementFeature;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ButtonSettings.class)
public class ButtonSettingsMixin {

    @Unique
    private static final ResourceLocation QUESTION_MARK = new ResourceLocation(
            "smixer",
            "gui/question.png"
    );

    @Shadow(remap = false)
    private static ResourceLocation GEAR;

    @Unique
    private ReplacementFeature replacementFeature;

    @Redirect(
            method = "drawButton",
            at = @At(
                    value = "FIELD",
                    target = "Lcodes/biscuit/skyblockaddons/gui/buttons/ButtonSettings;GEAR:Lnet/minecraft/util/ResourceLocation;",
                    opcode = Opcodes.GETSTATIC,
                    remap = false
            )
    )
    private ResourceLocation injectReplacedFeatureIconChangeToGearField() {
        if (this.replacementFeature == null) {
            return GEAR;
        }

        return QUESTION_MARK;
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    private void injectReplacementFeatureCheckToInit(
            double x,
            double y,
            String buttonText,
            SkyblockAddons main,
            Feature feature,
            CallbackInfo callback
    ) {
        this.replacementFeature = ReplacementFeature.forFeature(feature);
    }
}
