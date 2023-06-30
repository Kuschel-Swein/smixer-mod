package de.kuschel_swein.minecraft.smixer.mixins.sba.asm.hooks;

import codes.biscuit.skyblockaddons.asm.hooks.GuiIngameCustomHook;
import de.kuschel_swein.minecraft.smixer.contracts.sba.utils.MixedUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngameCustomHook.class)
public class GuiIngameCustomHookMixin {

    @Inject(
            method = "shouldRenderHealth",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void injectRiftCheckToHealthRenderer(CallbackInfoReturnable<Boolean> callback) {
        if (MixedUtils.getInstance().isInRift()) {
            callback.setReturnValue(true);
        }
    }
}
