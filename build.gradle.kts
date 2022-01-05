import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "sh.brocode"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    `java-library`
    id("com.diffplug.spotless") version "6.0.0"
    `maven-publish`
    signing
}

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
    // implementation("org.slf4j:slf4j-nop:1.7.32")
    testImplementation("io.kotest:kotest-runner-junit5:5.0.2")
    testImplementation("io.kotest:kotest-assertions-json:5.0.2")
    testImplementation("io.kotest:kotest-framework-datatest:5.0.2")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.upToDateWhen { false }
}

defaultTasks("clean", "test")

val spotlessCheckOrFormat = tasks.register("spotlessCheckOrFormat") {
    if (project.ext.has("ciBuild")) {
        dependsOn(tasks.spotlessCheck)
    } else {
        dependsOn(tasks.spotlessApply)
    }
}

tasks.withType<KotlinCompile> {
    dependsOn(spotlessCheckOrFormat)
}

configure<SpotlessExtension> {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        ktlint()
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
