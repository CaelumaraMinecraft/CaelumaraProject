plugins {
    id("java-library")
    id("aurika.repos")
    id("aurika.dependency")
}

dependencies {

    val kx: Dependency = compileOnlyApi(fileTree(rootDir.path + "\\libs\\plugins\\Kingdoms"))!!

    println("kc interfaces: ")
    for (inter in kx::class.java.interfaces) {
        println(inter)
    }
    println()

    preRelocationSettings {
        addPreRelocatedDependency(kx) {
            relocate("org.kingdoms.libs.kotlin", "kotlin")
            relocate("org.kingdoms.libs.jetbrains", "org.jetbrains")
            relocate("org.kingdoms.libs.checkerframework", "org.checkerframework")
            relocate("org.kingdoms.libs.intellij", "org.intellij")
            relocate("org.kingdoms.libs.mongodb", "com.mongodb")
            relocate("org.kingdoms.libs.caffeine", "com.github.benmanes.caffeine")
            relocate("org.kingdoms.libs.xseries", "com.cryptomorin.xseries")
        }
    }

}
