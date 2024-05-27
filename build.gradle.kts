import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    // https://kotlinlang.org/docs/releases.html#release-details
    kotlin("jvm") version "2.0.0"
    // https://github.com/jeremymailen/kotlinter-gradle/releases
    id("org.jmailen.kotlinter") version "4.3.0"
    // https://github.com/Kotlin/kotlinx-kover/releases
    id("org.jetbrains.kotlinx.kover") version "0.8.0"
}


group = "cat.mvmike.numbertotext"
version = "1.0.0"

// https://adoptium.net/temurin/releases/
val javaVersion = JavaVersion.VERSION_21
java.sourceCompatibility = javaVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // https://github.com/junit-team/junit5/releases
    val junitJupiterVersion = "5.10.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")

    // https://github.com/assertj/assertj-core/tags
    testImplementation("org.assertj:assertj-core:3.26.0")
}

tasks.apply {

    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.valueOf("JVM_$javaVersion"))
        }
    }

    test {
        enableAssertions = true
        useJUnitPlatform()
    }
}

kover {
    reports {
        verify {
            rule {
                bound {
                    minValue = 90
                }
            }
        }
    }
}

defaultTasks(
    //"formatKotlin",
    "lintKotlin",
    "clean",
    //"koverHtmlReport",
    "koverVerify",
    "build"
)
