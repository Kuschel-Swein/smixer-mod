package de.kuschel_swein.minecraft.smixer.mixins.sba.utils.enumutils;

import codes.biscuit.skyblockaddons.utils.EnumUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(EnumUtils.FeatureSetting.class)
public class FeatureSettingsMixin {

    @Shadow
    @Final
    @Mutable
    private static EnumUtils.FeatureSetting[] $VALUES;

    @Unique
    private static final EnumUtils.FeatureSetting HEALTH_BAR_HIDE_IN_RIFT = createEnumConstant(
            "HEALTH_BAR_HIDE_IN_RIFT",
            "smixer.settings.healthBarHideInRift",
            501
    );

    @Unique
    private static final EnumUtils.FeatureSetting HEALTH_TEXT_HIDE_IN_RIFT = createEnumConstant(
            "HEALTH_TEXT_HIDE_IN_RIFT",
            "smixer.settings.healthTextHideInRift",
            502
    );

    @Unique
    private static final EnumUtils.FeatureSetting HEALTH_SHOW_IN_RIFT = createEnumConstant(
            "HEALTH_SHOW_IN_RIFT",
            "smixer.settings.healthShowInRift",
            503
    );

    @Unique
    private static EnumUtils.FeatureSetting createEnumConstant(
            String internalName,
            String translationKey,
            int featureEquivalent
    ) {
        // noinspection ConstantConditions
        ArrayList<EnumUtils.FeatureSetting> currentValues = new ArrayList<>(Arrays.asList($VALUES));

        int internalId = (currentValues.get(currentValues.size() - 1).ordinal() + 1);

        EnumUtils.FeatureSetting newSetting = invokeInit(
                internalName,
                internalId,
                translationKey,
                featureEquivalent
        );

        currentValues.add(newSetting);

        $VALUES = currentValues.toArray(new EnumUtils.FeatureSetting[0]);

        return newSetting;
    }

    @Invoker("<init>")
    @SuppressWarnings("unused")
    public static EnumUtils.FeatureSetting invokeInit(
            String internalName,
            int internalId,
            String translationKey,
            int featureEquivalent
    ) {
        throw new AssertionError();
    }
}
