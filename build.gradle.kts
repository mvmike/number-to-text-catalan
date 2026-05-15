import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    // https://kotlinlang.org/docs/releases.html#release-details
    kotlin("jvm") version "2.3.21"
    // https://github.com/jeremymailen/kotlinter-gradle/releases
    id("org.jmailen.kotlinter") version "5.4.2"
    // https://github.com/Kotlin/kotlinx-kover/releases
    id("org.jetbrains.kotlinx.kover") version "0.9.8"
}


group = "cat.mvmike.numbertotext"
version = "1.0.0"

// https://adoptium.net/temurin/releases/
val javaVersion = JavaVersion.VERSION_25
java.sourceCompatibility = javaVersion

repositories {
    mavenCentral()
}

dependencies {
    // https://github.com/junit-team/junit5/releases
    val junitJupiterVersion = "6.0.3"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // https://github.com/assertj/assertj-core/tags
    testImplementation("org.assertj:assertj-core:3.27.7")
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
            events(SKIPPED, FAILED)
        }
        addTestListener(object : TestListener {
            override fun beforeSuite(suite: TestDescriptor) {}
            override fun beforeTest(testDescriptor: TestDescriptor) {}
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                // only print outermost Gradle Test Executor results
                suite.className ?: suite.parent?.let {
                    println(
                        "${result.resultType} " +
                            "(${result.testCount} tests - " +
                            "${result.successfulTestCount} successes, " +
                            "${result.failedTestCount} failures, " +
                            "${result.skippedTestCount} skipped)"
                    )
                }
            }
        })
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
    "lintKotlin",
    "clean",
    "koverVerify",
    "koverLog",
    "build"
)
