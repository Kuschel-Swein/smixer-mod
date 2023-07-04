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

import java.util.List;
import java.util.Set;

@Mixin(Utils.class)
public class UtilsMixin implements MixedUtils {

    @Unique
    private static final int RIFT_SCOREBOARD_LINE = 2;

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
            at = @At( // we could technically inject this check anywhere, but I guess its suited here the best
                    value = "INVOKE",
                    target = "Lcodes/biscuit/skyblockaddons/utils/Utils;isOnSkyblock()Z",
                    shift = At.Shift.BY,
                    by = -2,
                    remap = false
            )
    )
    private void injectRiftCheck(CallbackInfo callback) {
        // this isn't beautiful as we could also use the local vars for this,
        // BUT: there are like a million of them, so this is just the cleaner solution
        List<String> strippedScoreboardLines = ScoreboardManager.getStrippedScoreboardLines();

        if (strippedScoreboardLines != null && (strippedScoreboardLines.size() > RIFT_SCOREBOARD_LINE)) {
            String strippedLine = strippedScoreboardLines.get(RIFT_SCOREBOARD_LINE);

            // store if we are in the rift
            this.isInRift = (strippedLine != null && strippedLine.equals("Rift Dimension"));
        } else {
            this.isInRift = false;
        }
    }

    @Unique
    @Override
    public boolean isInRift() {
        return this.isInRift;
    }

}
