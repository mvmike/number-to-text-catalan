import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    // https://kotlinlang.org/docs/releases.html#release-details
    kotlin("jvm") version "2.1.0"
    // https://github.com/jeremymailen/kotlinter-gradle/releases
    id("org.jmailen.kotlinter") version "5.0.1"
    // https://github.com/Kotlin/kotlinx-kover/releases
    id("org.jetbrains.kotlinx.kover") version "0.9.0"
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
    // https://github.com/junit-team/junit5/releases
    val junitJupiterVersion = "5.11.4"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // https://github.com/assertj/assertj-core/tags
    testImplementation("org.assertj:assertj-core:3.27.0")
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
        testLogging {
            events(SKIPPED, FAILED, STANDARD_ERROR, STANDARD_OUT)
        }
        afterSuite(
            KotlinClosure2(
                { desc: TestDescriptor, result: TestResult ->
                    desc.parent
                        ?.let { return@KotlinClosure2 } // only the outermost suite
                        ?: println(
                            "${result.resultType} (" +
                                "${result.testCount} tests - " +
                                "${result.successfulTestCount} successes, " +
                                "${result.failedTestCount} failures, " +
                                "${result.skippedTestCount} skipped)"
                        )
                }
            )
        )
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
