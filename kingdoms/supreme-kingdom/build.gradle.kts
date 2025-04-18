plugins {
  id("java-library")
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

val targetJavaVersion: Int = 21

java {
  sourceCompatibility = JavaVersion.toVersion(targetJavaVersion)
  targetCompatibility = JavaVersion.toVersion(targetJavaVersion)
}

kotlin {
  jvmToolchain(targetJavaVersion)
}
