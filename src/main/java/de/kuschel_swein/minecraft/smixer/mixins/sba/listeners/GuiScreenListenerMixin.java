package de.kuschel_swein.minecraft.smixer.mixins.sba.listeners;

import codes.biscuit.skyblockaddons.listeners.GuiScreenListener;
import net.minecraft.inventory.InventoryBasic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreenListener.class)
public class GuiScreenListenerMixin {

    @Inject(
            method = "removeInventoryChangeListener",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void injectNullPointerPreventionToRemoveInventoryChangeListener(
            InventoryBasic inventory, CallbackInfo callback) {

        // this is not the fix to the actual problem, but it seems the easiest
        if (inventory == null) {
            callback.cancel();
        }
    }
}
