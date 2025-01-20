plugins {
    java
    kotlin("jvm") version "2.0.20-Beta2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "top.mckingdom.civilizations"
version = "0.2-SNAPSHOT"

//repositories {
//    mavenCentral()
//    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
//        name = "spigotmc-repo"
//    }
//    maven("https://oss.sonatype.org/content/groups/public/") {
//        name = "sonatype"
//    }
//}
//
//dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    compileOnly(depends.spigot)
//    compileOnly(files("$rootDir/libs/KingdomsX-1.17.1-ALPHA.jar"))
//    compileOnly(files("$rootDir/libs/kotlin-stdlib-2.0.20-Beta2-remapped.jar"))
//    compileOnly(files("$rootDir/libs/xseries-11.2.0.1-remapped.jar"))
//    compileOnly(files("$rootDir/libs/hikari-5.1.0-remapped.jar"))
//    compileOnly(files("$rootDir/libs/guava-33.2.1-jre-remapped.jar"))
//    compileOnly(files("$rootDir/libs/gson-2.11.0-remapped.jar"))
//    compileOnly(files("$rootDir/libs/caffeine-3.1.8-remapped.jar"))
//}

val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks{
    build {
        dependsOn("shadowJar")
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

}



