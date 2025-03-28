plugins {
  java
  kotlin("jvm") version "2.0.20-Beta2"
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "top.mckingdom.supreme_kingdom"
version = "0.2-SNAPSHOT"

dependencies {
  compileOnly(project(":v1.17:Auspice"))
}

val targetJavaVersion = 17
kotlin {
  jvmToolchain(targetJavaVersion)
}

tasks {

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


