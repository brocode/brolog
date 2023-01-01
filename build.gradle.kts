import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val slf4jVersion = "2.0.6"
val kotestVersion = "5.5.4"

group = "sh.brocode"
version = "0.4-SNAPSHOT"

plugins {
    val kotlinVersion = "1.8.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `java-library`
    id("com.diffplug.spotless") version "6.12.0"
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    // implementation("org.slf4j:slf4j-nop:$slf4jVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

configure<SpotlessExtension> {

    kotlin {
        ktlint().editorConfigOverride(
            mapOf(
                "ij_kotlin_allow_trailing_comma" to "true",
                "ij_kotlin_allow_trailing_comma_on_call_site" to "true"
            )
        )
        toggleOffOn()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    kotlinGradle {
        ktlint()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("brolog")
                description.set("A simple slf4j backend")
                url.set("https://github.com/brocode/brolog/")
                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("https://mit-license.org/")
                    }
                }
                developers {
                    developer {
                        id.set("p_haun")
                        name.set("Patrick Haun")
                        email.set("patrick@brocode.sh")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:brocode/brolog.git")
                    developerConnection.set("scm:git:ssh://git@github.com:brocode/brolog.git")
                    url.set("https://github.com/brocode/brolog/")
                }
            }
            from(components["java"])
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

nexusPublishing {
    repositories {
        sonatype { // only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
