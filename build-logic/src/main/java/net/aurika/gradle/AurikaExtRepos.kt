package net.aurika.gradle

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI

@get:JvmName("placeholderAPI")
val RepositoryHandler.placeholderAPI: MavenArtifactRepository
    get() = this.maven {
        url = URI.create("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        name = "placeholderAPI"
    }

@get:JvmName("sq89_repo")
val RepositoryHandler.sq89_repo: MavenArtifactRepository
    get() = this.maven {
        url = URI.create("https://maven.enginehub.org/repo/")
        name = "sq89-repo"
    }
