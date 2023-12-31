package de.kuschel_swein.minecraft.smixer.mixins.sba.core;

import codes.biscuit.skyblockaddons.core.Feature;
import codes.biscuit.skyblockaddons.core.GuiFeatureData;
import codes.biscuit.skyblockaddons.utils.EnumUtils;
import de.kuschel_swein.minecraft.smixer.abstraction.sba.utils.enumutils.MixedFeatureSetting;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Mixin(Feature.class)
public abstract class FeatureMixin {

    @Shadow(remap = false)
    @Final
    @Mutable
    private static Feature[] $VALUES;

    @Unique
    private static final Feature HEALTH_BAR_HIDE_IN_RIFT = createEnumConstant(
            "HEALTH_BAR_HIDE_IN_RIFT",
            501,
            "smixer.settings.healthBarHideInRift",
            false
    );

    @Unique
    private static final Feature HEALTH_TEXT_HIDE_IN_RIFT = createEnumConstant(
            "HEALTH_TEXT_HIDE_IN_RIFT",
            502,
            "smixer.settings.healthTextHideInRift",
            false
    );

    @Unique
    private static final Feature HEALTH_SHOW_IN_RIFT = createEnumConstant(
            "HEALTH_SHOW_IN_RIFT",
            503,
            "smixer.settings.healthShowInRift",
            false
    );

    @Unique
    private static final Feature DEFENCE_TEXT_HIDE_IN_RIFT = createEnumConstant(
            "DEFENCE_TEXT_HIDE_IN_RIFT",
            504,
            "smixer.settings.defenceTextHideInRift",
            false
    );

    @Shadow(remap = false)
    @Final
    private static Set<Feature> SETTINGS;

    @Shadow(remap = false)
    @Final
    private static int ID_AT_PREVIOUS_UPDATE;

    @Shadow(remap = false)
    @Final
    private int id;

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void injectUpdatedSettingsId(CallbackInfo callback) {
        setIdAtPreviousUpdate(500);
    }

    @Accessor(value = "ID_AT_PREVIOUS_UPDATE", remap = false)
    @SuppressWarnings("unused")
    public static void setIdAtPreviousUpdate(int id) {
        throw new AssertionError();
    }

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void injectUpdatedConstants(CallbackInfo callback) {
        Feature.HEALTH_BAR.getSettings().add(MixedFeatureSetting.HEALTH_BAR_HIDE_IN_RIFT.getFeatureSetting());
        Feature.HEALTH_TEXT.getSettings().add(MixedFeatureSetting.HEALTH_TEXT_HIDE_IN_RIFT.getFeatureSetting());
        Feature.HIDE_HEALTH_BAR.getSettings().add(MixedFeatureSetting.HEALTH_SHOW_IN_RIFT.getFeatureSetting());
        Feature.DEFENCE_TEXT.getSettings().add(MixedFeatureSetting.DEFENCE_TEXT_HIDE_IN_RIFT.getFeatureSetting());

        // sort our new constants
        SETTINGS.addAll(
                Arrays.asList(
                        HEALTH_BAR_HIDE_IN_RIFT,
                        HEALTH_TEXT_HIDE_IN_RIFT,
                        HEALTH_SHOW_IN_RIFT,
                        DEFENCE_TEXT_HIDE_IN_RIFT
                )
        );
    }

    @Unique
    private static Feature createEnumConstant(
            String internalName,
            int id,
            String translationKey,
            boolean defaultDisabled
    ) {
        return createEnumConstant(internalName, id, translationKey, null, defaultDisabled);
    }

    @Unique
    private static Feature createEnumConstant(
            String internalName,
            int id,
            String translationKey,
            GuiFeatureData guiFeatureData,
            boolean defaultDisabled,
            EnumUtils.FeatureSetting... settings
    ) {
        ArrayList<Feature> currentValues = new ArrayList<>(Arrays.asList(FeatureMixin.$VALUES));

        int internalId = (currentValues.get(currentValues.size() - 1).ordinal() + 1);

        Feature newFeature = invokeInit(
                internalName,
                internalId,
                id,
                translationKey,
                guiFeatureData,
                defaultDisabled,
                settings
        );

        currentValues.add(newFeature);

        $VALUES = currentValues.toArray(new Feature[0]);

        return newFeature;
    }

    @Invoker("<init>")
    @SuppressWarnings("unused")
    public static Feature invokeInit(
            String internalName,
            int internalId,
            int id,
            String translationKey,
            GuiFeatureData guiFeatureData,
            boolean defaultDisabled,
            EnumUtils.FeatureSetting... settings
    ) {
        throw new AssertionError();
    }

    @Inject(
            method = "isNew",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void injectVariableCheckToIsNew(CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue((this.id > ID_AT_PREVIOUS_UPDATE));
    }
}
