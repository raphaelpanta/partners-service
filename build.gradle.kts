import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

buildscript {
    repositories {
        mavenLocal()
        jcenter {
            content {
                includeGroup("org.jetbrains.kotlinx")
            }
        }
        mavenCentral()
    }
}

plugins {
    base
    kotlin("jvm") version "1.4.10"
    id("io.gitlab.arturbosch.detekt").version("1.14.0")
}

detekt {
    failFast = true
    buildUponDefaultConfig = true

    reports {
        html.enabled = true
    }
}

allprojects {
    group = "com.github.raphaelpanta.partiners"
    version = "1.0.0"

    repositories {
        jcenter()
    }

    tasks.withType<Test> {
        dependencies {

            testImplementation(platform("org.junit:junit-bom:5.7.0"))
            testImplementation("org.junit.jupiter:junit-jupiter")

            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }


    detekt {
        input = objects.fileCollection().from(
                DEFAULT_SRC_DIR_JAVA,
                "src/test/java",
                DEFAULT_SRC_DIR_KOTLIN,
                "src/test/kotlin"
        )

        buildUponDefaultConfig = true

        reports {
            html.enabled = true
        }
    }
}

val analysisDir = file(projectDir)

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val detektFormat by tasks.registering(Detekt::class) {
    description = "Formats whole project."
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    reports {
        html.enabled = true
    }
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs the whole project at once."
    parallel = true
    buildUponDefaultConfig = true
    setSource(analysisDir)
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    reports {
        html.enabled = true
    }
}

val detektProjectBaseline by tasks.registering(DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)
    setSource(analysisDir)
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
}