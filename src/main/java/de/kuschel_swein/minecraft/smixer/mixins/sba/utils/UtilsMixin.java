package de.kuschel_swein.minecraft.smixer.mixins.sba.utils;

import codes.biscuit.skyblockaddons.utils.ScoreboardManager;
import codes.biscuit.skyblockaddons.utils.Utils;
import de.kuschel_swein.minecraft.smixer.contracts.sba.utils.MixedUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(Utils.class)
public class UtilsMixin implements MixedUtils {

    @Final
    @Shadow(remap = false)
    private static Set<String> SKYBLOCK_IN_ALL_LANGUAGES;

    @Unique
    private boolean isInRift;

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void injectAprilFoolsProtection(CallbackInfo callback) {
        // just in case admins try it again...
        SKYBLOCK_IN_ALL_LANGUAGES.add("SKIBLOCK");
    }

    @Inject(
            method = "parseSidebar",
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Lcodes/biscuit/skyblockaddons/core/Location;getScoreboardName()Ljava/lang/String;",
                    shift = At.Shift.BY,
                    by = -2,
                    remap = false
            )
    )
    private void injectRiftCheck(CallbackInfo callback) {
        // this isn't beautiful as we could also use the local vars for this,
        // BUT: there are like a million of them, so this is just the cleaner solution
        String strippedLine = ScoreboardManager.getStrippedScoreboardLines().get(4);

        // store if we are in the rift
        this.isInRift = strippedLine.equals("The Rift");
    }

    @Unique
    @Override
    public boolean isInRift() {
        return this.isInRift;
    }

}
