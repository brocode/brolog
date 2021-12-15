import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
    id("com.diffplug.spotless") version "6.0.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.32")
    // implementation("org.slf4j:slf4j-nop:1.7.32")
    testImplementation("io.kotest:kotest-runner-junit5:5.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

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
