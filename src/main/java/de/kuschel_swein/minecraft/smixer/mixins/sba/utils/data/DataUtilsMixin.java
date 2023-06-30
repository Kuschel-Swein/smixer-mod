package de.kuschel_swein.minecraft.smixer.mixins.sba.utils.data;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import codes.biscuit.skyblockaddons.config.ConfigValues;
import codes.biscuit.skyblockaddons.core.Language;
import codes.biscuit.skyblockaddons.utils.data.DataUtils;
import com.google.gson.JsonObject;
import de.kuschel_swein.minecraft.smixer.SmixerMod;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Mixin(DataUtils.class)
public class DataUtilsMixin {

    @Inject(
            method = "loadLocalizedStrings(Lcodes/biscuit/skyblockaddons/core/Language;Z)V",
            at = @At("TAIL"),
            remap = false
    )
    private static void addSmixerTranslationsToLoadLocalizedStrings(
            Language language,
            boolean loadOnlineStrings,
            CallbackInfo callback
    ) {
        ConfigValues configValues = SkyblockAddons.getInstance().getConfigValues();
        JsonObject languageConfig = configValues.getLanguageConfig();

        languageConfig.add("smixer", loadSmixerLanguage(language));
    }

    @Unique
    private static JsonObject loadSmixerLanguage(Language language) {
        URL resourceUrl = SmixerMod.class.getClassLoader().getResource(
                "lang/smixer/" + language.getPath() + ".json"
        );

        try {
            Objects.requireNonNull(resourceUrl);

            String fileContent = IOUtils.toString(resourceUrl, StandardCharsets.UTF_8);

            return SkyblockAddons.getGson().fromJson(fileContent, JsonObject.class);
        } catch (IOException | NullPointerException e) {
            // well, too bad we can't load our lang file (it probably doesn't exist)

            return new JsonObject();
        }
    }
}
