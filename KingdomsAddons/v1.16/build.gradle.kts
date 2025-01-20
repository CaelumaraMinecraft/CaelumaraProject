
subprojects {
    dependencies {
        compileOnly(fileTree("$rootDir/v1.16/&libs"))
        compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
        compileOnly("org.checkerframework:checker-qual:3.46.0")
    }
}