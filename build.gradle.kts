import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    // https://kotlinlang.org/docs/releases.html#release-details
    kotlin("jvm") version "1.9.10"
}


group = "cat.mvmike.numbertotext"
version = "1.0.0"

// https://adoptium.net/temurin/releases/
val javaVersion = JavaVersion.VERSION_17
java.sourceCompatibility = javaVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // https://github.com/junit-team/junit5/releases
    val junitJupiterVersion = "5.10.0"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")

    // https://github.com/assertj/assertj-core/tags
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.apply {

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    test {
        enableAssertions = true
        useJUnitPlatform()
    }
}

defaultTasks(
    "clean",
    "build"
)
