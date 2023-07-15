package de.kuschel_swein.minecraft.smixer.mixins.sba.utils;

import codes.biscuit.skyblockaddons.utils.TextUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Pattern;

@Mixin(TextUtils.class)
public class TextUtilsMixin {

    @Shadow
    @Final
    @Mutable
    private static Pattern STRIP_ICONS_PATTERN;

    @Inject(
            method = "<clinit>",
            at = @At("TAIL"),
            remap = false
    )
    private static void injectAdditionalIconsToStripIconsPattern(CallbackInfo callback) {
        // while it may seem that this pattern is too greedy and will "eat" too much other characters
        // it's well suited for this place here, as we really want to strip ALL icons
        STRIP_ICONS_PATTERN = Pattern.compile("[^\\w \\-\\[\\]]");
    }
}
