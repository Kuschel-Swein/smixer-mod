import de.undercouch.gradle.tasks.download.Download
import dev.architectury.pack200.java.Pack200Adapter
import java.text.NumberFormat
import java.util.*

plugins {
    id("gg.essential.loom")
    id("io.github.juuxel.loom-quiltflower")
    id("dev.architectury.architectury-pack200")
    id("com.github.johnrengelman.shadow")
    id("de.undercouch.download") version "5.4.0"
    id("net.kyori.blossom") version "1.3.1"
}

group = "de.kuschel_swein.minecraft.smixer"
version = "1.0.0"

blossom {
    val modVersion = project.version;

    if (project.hasProperty("isCiBuild")) {
        if (!project.hasProperty("buildNumber") || !project.hasProperty("runAttempt")) {
            throw InvalidUserDataException("Invalid CI-Parameters given!")
        }

        val buildNumber = project.property("buildNumber")
        val runAttempt = project.property("runAttempt")

        val numberFormatter = NumberFormat.getIntegerInstance(Locale.US)
        val runAttemptNumber = numberFormatter.parse(runAttempt.toString()).toInt()
        val shouldAddRunAttempt = (runAttemptNumber > 1)

        if (shouldAddRunAttempt) {
            project.setProperty("buildNumber", "${buildNumber}.${runAttemptNumber - 1}")
        }

        project.version = "${project.version}+" + project.property("buildNumber")

    }

    replaceTokenIn("src/main/java/de/kuschel_swein/minecraft/smixer/SmixerMod.java")
    replaceToken("\$VERSION", modVersion)
}

loom {
    runConfigs {
        named("client") {
            ideConfigGenerated(true)
        }
    }

    launchConfigs {
        getByName("client") {
            property("mixin.debug", "true")

            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
            arg("--mixin", "mixins.smixer.json")
        }
    }

    forge {
        pack200Provider.set(Pack200Adapter())
        mixinConfig("mixins.smixer.json")

        // required to use sba in dev-mode
        accessTransformer("src/main/resources/sba_at.cfg")
    }
}

val embed: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    compileOnly("gg.essential:essential-1.8.9-forge:4955+g395141645")
    embed("gg.essential:loader-launchwrapper:1.1.3")

    compileOnly("org.spongepowered:mixin:0.8.5-SNAPSHOT")
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")

    modImplementation(files(
            "libs/SkyblockAddons-" + project.property("sba.version") + "-for-MC-1.8.9.jar"
    ))
}

repositories {
    maven("https://repo.essential.gg/repository/maven-public")
    maven("https://repo.spongepowered.org/repository/maven-public")
}

task<Download>("downloadSkyblockAddons") {
    group = "build setup"

    val sbaVersion = project.property("sba.version");

    src("https://github.com/BiscuitDevelopment/SkyblockAddons/releases/download/v$sbaVersion/SkyblockAddons-$sbaVersion-for-MC-1.8.9.jar")
    dest("libs")
}

sourceSets {
    main {
        // forge wants our resources in the classes directory
        output.setResourcesDir(java.classesDirectory)
    }
}


tasks {

    jar {
        from(embed.files.map { zipTree(it) })

        manifest.attributes(
                mapOf(
                        "ModSide" to "CLIENT",
                        "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker",
                        "MixinConfigs" to "mixins.smixer.json"
                )
        )
    }

    processResources {
        inputs.property("version", project.version)
        inputs.property("mcversion", "1.8.9")
        filesMatching("mcmod.info") {
            expand("version" to project.version, "mcversion" to "1.8.9")
        }
    }

    withType<JavaCompile> {
        options.release.set(8)
    }
}
