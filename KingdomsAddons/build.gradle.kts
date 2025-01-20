plugins {
    java
//    kotlin("jvm") version "2.0.20-Beta2"
//    id("io.github.goooler.shadow") version "8.1.1"
}

group = "org.mckingdom"

subprojects {

    apply(plugin = "java")
//    apply(plugin = "kotlin")
//    apply(plugin = "io.github.goooler.shadow")

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "spigotmc-repo"
        }
        maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype"
        }
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.auxilor.io/repository/maven-public/")
        maven("https://repo.codemc.org/repository/nms/")
        maven("https://repo.essentialsx.net/releases/")
        maven("https://jitpack.io") {
            content { includeGroupByRegex("com\\.github\\..*") }
        }
    }

//    dependencies {
//        compileOnly(fileTree(this@subprojects.projectDir.toString() + "libs"))
//    }

//    tasks {
//        compileKotlin {
//            kotlinOptions {
//                jvmTarget = "17"
//            }
//        }
//
//        build {
//            dependsOn(shadowJar)
//        }
//
//    }
//
//
//    java {
//        withSourcesJar()
//        toolchain {
//            languageVersion = JavaLanguageVersion.of(21)
//        }
//
//    }

}