plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
    java
}

group = "top.mckingdom.uninvade"
version = "0.2-SNAPSHOT"


val targetJavaVersion = 17
kotlin {
    jvmToolchain(targetJavaVersion)
}


tasks {

    build {
        dependsOn("shadowJar")
    }

    shadowJar {

        archiveBaseName = "KingdomsUninvade"
        archiveClassifier = null

        relocate("kotlin", "org.kingdoms.libs.kotlin")

        exclude(
            "kotlin-stdlib-2.0.20-Beta2.jar",
            "annotations-13.0.jar",
            "checker-qual-3.46.0.jar"
        )
    }

    jar {
        enabled = false
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
