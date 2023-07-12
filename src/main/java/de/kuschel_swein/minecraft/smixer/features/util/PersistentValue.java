package de.kuschel_swein.minecraft.smixer.features.util;

import codes.biscuit.skyblockaddons.SkyblockAddons;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import de.kuschel_swein.minecraft.smixer.SmixerMod;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class PersistentValue<T> {

    private static final Logger LOGGER = LogManager.getLogger(PersistentValue.class);

    private static final Gson GSON = SkyblockAddons.getGson();

    private static final String PERSISTENCE_DIRECTORY = "persistence";

    private static final String FILE_EXTENSION = ".json";

    private final String identifier;

    private final File storageFile;

    private T value;

    public PersistentValue(String identifier, T defaultValue) {
        this.identifier = identifier;
        this.storageFile = new File(
                Loader.instance().getConfigDir(),
                SmixerMod.MOD_ID + File.separator +
                        PERSISTENCE_DIRECTORY + File.separator +
                        identifier + FILE_EXTENSION
        );

        this.value = defaultValue;

        // load initially from disk
        this.loadFromDisk();
    }

    public void loadFromDisk() {
        try {
            String json = FileUtils.readFileToString(this.storageFile);

            this.value = GSON.fromJson(json, new TypeToken<T>() {
            }.getType());
        } catch (IOException | JsonSyntaxException e) {
            LOGGER.warn("Could not read persisted value for identifier '" + this.identifier + "', did not mutate anything.", e);
        }
    }

    public void saveToDisk() {
        try {
            String json = GSON.toJson(this.value);

            FileUtils.writeStringToFile(this.storageFile, json);
        } catch (IOException e) {
            LOGGER.warn("Could not save persisted value for identifier '" + this.identifier + "'.", e);
        }
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}
