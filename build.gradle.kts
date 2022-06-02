import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "sh.brocode"
version = "0.2-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    `java-library`
    id("com.diffplug.spotless") version "6.6.1"
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
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    // implementation("org.slf4j:slf4j-nop:1.7.32")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.0")
    testImplementation("io.kotest:kotest-assertions-json:5.3.0")
    testImplementation("io.kotest:kotest-framework-datatest:5.3.0")
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
