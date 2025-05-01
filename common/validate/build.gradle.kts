import net.aurika.gradle.AurikaDependencies.JETBRAINS_ANNOTATIONS

plugins {
  id("java-library")
  id("maven-publish")
  id("net.kyori.indra")
}

version = "0.0.1"
description = "A library to validate something"

dependencies {
  compileOnly(JETBRAINS_ANNOTATIONS)
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

indra {
  javaVersions {
    target(8)
    minimumToolchain(8)
  }
}

sourceSets {
  main {
    multirelease {
      alternateVersions(9)
      moduleName("net.aurika.common.validate")
      configureVariants {  }
    }
  }
}
