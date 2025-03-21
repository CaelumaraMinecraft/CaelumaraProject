plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
//    id("org.gradle.kotlin.kotlin-dsl")
//    id("org.gradle.java-gradle-plugin")
}

repositories {
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    project.gradle.parent?.rootProject?.repositories?.let { this.addAll(it) }
}

dependencies {
    compileOnly(localGroovy())
    implementation("org.ow2.asm:asm:9.7")           // 用于字节码修改
    implementation("org.ow2.asm:asm-commons:9.7")   // 用于类重定向
//    implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.3")
    implementation("net.kyori", "indra-common", "3.1.3")
    implementation("com.gradleup.shadow", "shadow-gradle-plugin", "8.3.3")
    implementation("xyz.jpenilla", "run-task", "2.3.1")
}
