package de.kuschel_swein.minecraft.smixer.mixins.sba.listeners;

import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.gui.buttons.ButtonLocation;
import codes.biscuit.skyblockaddons.listeners.RenderListener;
import de.kuschel_swein.minecraft.smixer.abstraction.sba.core.MixedFeature;
import de.kuschel_swein.minecraft.smixer.contracts.sba.utils.MixedUtils;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderListener.class)
public class RenderListenerMixin {

    @Inject(
            method = "drawBar",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void injectRiftCheckToDrawBar(
            Feature feature, float scale, Minecraft mc,
            @Nullable ButtonLocation resizingGui, CallbackInfo callback) {

        if (resizingGui == null) {
            if (feature == Feature.HEALTH_BAR) {
                if (MixedUtils.getInstance().isInRift()) {
                    if (MixedFeature.HEALTH_BAR_HIDE_IN_RIFT.isEnabled()) {
                        callback.cancel();
                    }
                }
            }
        }
    }

    @Inject(
            method = "drawText",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void injectRiftCheckToDrawText(
            Feature feature, float scale, Minecraft mc,
            @Nullable ButtonLocation resizingGui, CallbackInfo callback) {

        if (resizingGui == null) {
            if (feature == Feature.HEALTH_TEXT) {
                if (MixedUtils.getInstance().isInRift()) {
                    if (MixedFeature.HEALTH_TEXT_HIDE_IN_RIFT.isEnabled()) {
                        callback.cancel();
                    }
                }
            }
        }
    }
}
