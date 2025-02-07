rootProject.name = "KingdomsAddons"

include(":v1.16")
include(":v1.16:Uninvade")
include(":v1.17")
include(":v1.17:Auspice")
include(":v1.17:Powerful-Territory")
include(":v1.17:Props")
include(":v1.17:Civilizations")
include(":v1.17:Supreme-Kingdom")
include(":v1.17:Trade-Point")
include(":v1.17:PlaceholderAPI-Bridge")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.auxilor.io/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "spigotmc-repo"
        }
        maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype"
        }
        maven("https://repo.codemc.org/repository/nms/")
        maven("https://repo.essentialsx.net/releases/")
        maven("https://jitpack.io") {
            content { includeGroupByRegex("com\\.github\\..*") }
        }
    }
}
