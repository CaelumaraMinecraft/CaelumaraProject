package net.aurika.gradle.repository

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.ArtifactRepository
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI

@get:JvmName("sonatype")
val RepositoryHandler.sonatype: MavenArtifactRepository
  get() = maven {
    MavenArtifactRepository.setUrl = URI.create("https://oss.sonatype.org/content/groups/public/")
    ArtifactRepository.setName = "sonatype"
  }
