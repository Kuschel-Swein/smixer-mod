package de.kuschel_swein.minecraft.smixer.mixins.sba.gui;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.Translations;
import codes.biscuit.skyblockaddons.gui.SettingsGui;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonTextNew;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import de.kuschel_swein.minecraft.smixer.abstraction.smixer.ReplacementFeature;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Mixin(SettingsGui.class)
public abstract class SettingsGuiMixin extends GuiScreen {

    @Unique
    private ReplacementFeature replacementFeature;

    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    private void injectReplacementFeatureCheckToInit(
            Feature feature,
            int page,
            int lastPage,
            EnumUtils.GuiTab lastTab,
            List<EnumUtils.FeatureSetting> settings,
            CallbackInfo callback
    ) {
        this.replacementFeature = ReplacementFeature.forFeature(feature);
    }

    @Inject(
            method = "initGui",
            at = @At("TAIL")
    )
    private void injectReplacementTextsToInitGui(CallbackInfo callback) {
        if (this.replacementFeature != null) {
            this.buttonList.clear();

            int halfWidth = (this.width / 2);
            double y = this.getRowHeightSetting(1);
            String replacementId = this.replacementFeature.getModId();
            boolean isReplacementInstalled = SkyblockAddons.getInstance().getUtils().isModLoaded(replacementId);

            this.buttonList.addAll(
                    Arrays.asList(
                            new ButtonTextNew(
                                    halfWidth,
                                    (int) (y - 10),
                                    Translations.getMessage(
                                            "smixer.settings.messages.replacement.replacedWith",
                                            replacementId
                                    ),
                                    true,
                                    0xFFFFFFFF
                            ),
                            new ButtonTextNew( // ToDo: open url on click (maybe)
                                    halfWidth,
                                    (int) (y + 10),
                                    isReplacementInstalled ?
                                            Translations.getMessage("smixer.settings.messages.replacement.isInstalled") :
                                            Translations.getMessage(
                                                    "smixer.settings.messages.replacement.isNotInstalled",
                                                    replacementId
                                            ),
                                    true,
                                    isReplacementInstalled ? 0x44bd32 : 0xfbc531
                            )
                    )
            );
        }
    }

    @Shadow
    protected abstract double getRowHeightSetting(double row);

    @ModifyArgs(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lcodes/biscuit/skyblockaddons/gui/SkyblockAddonsGui;drawScaledString(Lnet/minecraft/client/gui/GuiScreen;Ljava/lang/String;IIDI)V",
                    remap = false
            )
    )
    private void injectReplacementFeatureTextToDrawScreen(Args args) {
        int colorValue = args.get(3);

        if (this.replacementFeature != null) {
            // it's way easier to manually calculate the alpha value instead of capturing the locals
            int alpha = (colorValue >> 24) & 0xff;
            String newText = Translations.getMessage("smixer.settings.messages.replacement.replaced");
            Color newColor = new Color(222, 68, 76, alpha);

            args.set(1, newText);
            args.set(3, newColor.getRGB());
        }
    }
}
