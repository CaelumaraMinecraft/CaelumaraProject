plugins {
  `java-library`
  kotlin("jvm")
}

group = "net.aurika.kingdoms.supreme_kingdom"
version = "0.2-SNAPSHOT"

dependencies {
  compileOnly(project(":auspice"))
}

kingdoms {
  addon {
    isAddonInterface = true
    addonName = "Supreme-Kingdom"
  }
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


