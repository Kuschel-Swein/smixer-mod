import dev.architectury.pack200.java.Pack200Adapter

plugins {
    id("gg.essential.loom")
    id("io.github.juuxel.loom-quiltflower")
    id("dev.architectury.architectury-pack200")
    id("com.github.johnrengelman.shadow")
}

group = "de.kuschel_swein.minecraft.smixer"
version = "1.0.0"

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
        accessTransformer("src/main/resources/sba_at.cfg")
        accessWidenerPath.set(file("src/main/resources/smixer.accesswidener"))
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

    modImplementation(files("libs/SkyblockAddons-1.7.2-for-MC-1.8.9.jar"))
}

repositories {
    maven("https://repo.essential.gg/repository/maven-public")
    maven("https://repo.spongepowered.org/repository/maven-public")
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
