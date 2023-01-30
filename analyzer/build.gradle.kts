import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "si.ivory"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-util:9.4")
    implementation("org.ow2.asm:asm-tree:9.4")
    implementation("guru.nidi:graphviz-java-all-j2v8:0.18.1")
    implementation("guru.nidi:graphviz-kotlin:0.18.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
