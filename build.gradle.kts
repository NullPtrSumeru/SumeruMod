import java.text.SimpleDateFormat
import java.util.*
import org.gradle.api.file.FileCollection
import java.util.List

buildscript {
    repositories {
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
        maven(url="https://mvnrepository.com/artifact/")
        jcenter()

        gradlePluginPortal()

        maven(url = "https://maven.mcmoddev.com/")
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.apache.org/")
        maven(url = "https://repository.ow2.org/nexus/")
        maven{
           url = uri("https://libraries.minecraft.net")
        }
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")

        flatDir {
		dirs(project.file("libs"))
	}

        maven(url = "https://repo.spring.io/milestone")
    }
    dependencies {
        classpath("org.gradlex:extra-java-module-info:1.5")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("com.github.johnrengelman:shadow:7.0.0")
    }
}

plugins {
    id("net.minecraftforge.gradle") version "6.+"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("java")
    id("wtf.gofancy.fancygradle") version "1.1.+"
    kotlin("jvm") version "1.9.10"
    id("org.gradlex.extra-java-module-info") version "1.5"
    `maven-publish`
}

apply {
    plugin("java-base")
    plugin("kotlin")
    plugin("eclipse")
    plugin("org.gradlex.extra-java-module-info")
    plugin("java")
    plugin("net.minecraftforge.gradle")
    plugin("wtf.gofancy.fancygradle")
    plugin("com.github.johnrengelman.shadow")
    plugin("maven-publish")
}

fancyGradle {
	patches {
		resources
		coremods
		asm
		mergetool
	}
}

sourceSets.main.configure {
	kotlin.srcDirs += project.file("src/main/kotlin")
	java.srcDirs += project.file("src/main/java")
	resources.srcDirs += project.file("src/generated/resources")
}

version = "0.1"
java { 
     toolchain { 
         languageVersion.set(JavaLanguageVersion.of(8))
     }
}

group = "com.nullptr.mod"

minecraft {
    mappings("stable", "39-1.12")
	runs {
		configureEach {
			workingDirectory(project.file("run"))
			property("forge.logging.markers", "REGISTRIES")
			property("forge.logging.console.level", "debug")
			mods {
				create(project.name) {
					source(sourceSets.main.get())
				}
			}
		}

		create("client") {
		}

		create("server") {
			args("--nogui")
		}
		create("data") {
			workingDirectory(project.file("run-data"))
			args(
				"--mod",
				"mod",
				"--all",
				"--output",
				project.file("src/generated/resources/"),
				"--existing",
				project.file("src/main/resources/")
			)
		}
	}
}



extraJavaModuleInfo { 
     failOnMissingModuleInfo.set(false) 
}

configurations {
	library
	implementation.extendsFrom library
	shadow.extendsFrom library
}

minecraft.runs.all {
	lazyToken("minecraft_classpath") {
		configurations.library.copyRecursive().resolve().collect { it.absolutePath }.join(File.pathSeparator)
	}
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    minecraft(group = "net.minecraftforge", name = "forge", version = "1.12.2-14.23.5.2860")
    implementation("club.minnced:discord-webhooks:0.8.4")
    implementation("com.theokanning.openai-gpt3-java:service:0.12.0")
    implementation(files("JDA-4.4.1_353-withDependencies-no-opus.jar"))
    shadow("com.theokanning.openai-gpt3-java:service:0.12.0")
    shadow(files("JDA-4.4.1_353-withDependencies-no-opus.jar"))
}

jar {
        archiveBaseName.set("mod")

        manifest {
            attributes(
                hashMapOf(
                    "Specification-Title" to "examplemod",
                    "Specification-Vendor" to "examplemodsareus",
                    "Specification-Version" to "1",
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to "${version}",
                    "Implementation-Vendor" to "examplemodsareus",
                    "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
                )
            )
        }
        finalizedBy("reobfJar")
}

shadowJar {
       configurations = [project.configurations.shadow]
       archiveBaseName.set("shadow") 
       archiveClassifier.set("") 
       archiveVersion.set("")
       manifest.inheritFrom(jar.manifest.getAttributes()) 
}
reobf {
	shadowJar {}
}
task sourcesJar(type: Jar, dependsOn: classes) {
	archiveClassifier.set("sources")
	from sourceSets.main.allSource
}
tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
}

artifacts {
	archives jar
	archives shadowJar
	archives sourcesJar
	//archives javadocJar
}



processResources {
	duplicatesStrategy = "include"

	inputs.property "version", project.version
	inputs.property "mcversion", modMinecraftVersion

	from(sourceSets.main.resources.srcDirs) {
		include("mcmod.info")
		expand("version": project.version, "mcversion": modMinecraftVersion)
	}

	from(sourceSets.main.resources.srcDirs) {
		exclude("mcmod.info")
	}
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven(
            url = "file:///${project.projectDir}/mcmodsrepo"
        )
    }
}
