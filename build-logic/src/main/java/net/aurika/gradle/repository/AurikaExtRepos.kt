package net.aurika.gradle.repository

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI

@get:JvmName("sonatype")
val RepositoryHandler.sonatype: MavenArtifactRepository
    get() = maven {
        url = URI.create("https://oss.sonatype.org/content/groups/public/")
        name = "sonatype"
    }

//@get:JvmName("FabricMC")
//val RepositoryHandler.FabricMC: MavenArtifactRepository
//    get() = maven {
//        url = URI.create("https://maven.fabricmc.net/")
//        name = "FabricMC"
//    }

//@get:JvmName("PurpurMC_snapshots")
//val RepositoryHandler.PurpurMC_snapshots: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.purpurmc.org/snapshots")
//        name = "PurpurMC-snapshots"
//    }

//@get:JvmName("minecraft")
//val RepositoryHandler.minecraft: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://libraries.minecraft.net")
//        name = "minecraft"
//    }


//@get:JvmName("nostal_snapshots")
//val RepositoryHandler.nostal_snapshots: MavenArtifactRepository
//    get() = maven {
//        url = URI.create("https://maven.nostal.ink/repository/maven-snapshots/")
//        name = "nostal-snapshots"
//    }

//@get:JvmName("sq89_repo")
//val RepositoryHandler.sq89_repo: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://maven.enginehub.org/repo/")
//        name = "sq89-repo"
//    }

//@get:JvmName("auxilor")
//val RepositoryHandler.auxilor: MavenArtifactRepository
//    get() = maven {
//        url = URI.create("https://repo.auxilor.io/repository/maven-public/")
//        name = "auxilor-repo"
//    }

//@get:JvmName("thenextlvl_releases")
//val RepositoryHandler.thenextlvl_releases: MavenArtifactRepository
//    get() = maven {
//        url = URI.create("https://repo.thenextlvl.net/releases")
//        name = "thenextlvl-releases"
//    }

//@get:JvmName("essentials_releases")
//val RepositoryHandler.essentials_releases: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.essentialsx.net/releases/")
//        name = "essentials-releases"
//    }

//@get:JvmName("essentials_snapshots")
//val RepositoryHandler.essentials_snapshots: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://nexus.scarsz.me/content/groups/public/")
//        name = "essentials-snapshots"
//    }

//@get:JvmName("matteodev")
//val RepositoryHandler.matteodev: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://maven.devs.beer/")
//        name = "matteodev"
//    }

//@get:JvmName("oraxen_releases")
//val RepositoryHandler.oraxen_releases: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.oraxen.com/releases")
//        name = "oraxen-releases"
//    }

//@get:JvmName("oraxen_snapshots")
//val RepositoryHandler.oraxen_snapshots: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.oraxen.com/snapshots")
//        name = "oraxen-snapshots"
//    }

//@get:JvmName("placeholderAPI")
//val RepositoryHandler.placeholderAPI: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.extendedclip.com/content/repositories/placeholderapi/")
//        name = "placeholderAPI"
//    }


//@get:JvmName("mvdw_software")
//val RepositoryHandler.mvdw_software: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.mvdw-software.be/content/groups/public")
//        name = "mvdw-software"
//    }

//@get:JvmName("imanity")
//val RepositoryHandler.imanity: MavenArtifactRepository
//    get() = this.maven {
//        url = URI.create("https://repo.imanity.dev/imanity-libraries")
//        name = "imanity"
//    }
