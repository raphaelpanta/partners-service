import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    jacoco
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

detekt {
    failFast = true
    buildUponDefaultConfig = true

    reports {
        html.enabled = true
        reportsDir = file("$buildDir/jacoco/reports")
    }
}

allprojects {
    group = "com.github.raphaelpanta.partners"
    version = "1.0.0"

    repositories {
        jcenter()
    }

    tasks.withType<Test> {
        dependencies {

            testImplementation(platform("org.junit:junit-bom:5.7.0"))
            testImplementation("org.junit.jupiter:junit-jupiter")
            testImplementation("io.strikt:strikt-core:0.28.0")
            testImplementation("io.mockk:mockk:1.10.2")

            useJUnitPlatform()
            testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_14.toString()
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("jacoco")
        plugin("com.github.johnrengelman.shadow")
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

    jacoco {
        toolVersion = "0.8.6"
        reportsDir = file("$buildDir/jacoco/reports")
    }

    tasks.withType<Detekt> {
        exclude("resources/")
        exclude("build/")
    }

    tasks.withType<Test> {
        finalizedBy(tasks.withType<JacocoReport>())
    }

    tasks.withType<JacocoReport> {
        dependsOn(tasks.withType<Test>())
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

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        rule {
            enabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}

shadow {
    dependencies {
        implementation(project(":partners-service:partners-api"))
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.github.raphaelpanta.partners.MainKt"
    }
}

tasks.register<JacocoReport>("codeCoverageReport") {
    dependsOn(subprojects.map { it.tasks.withType<Test>() })
    dependsOn(subprojects.map { it.tasks.withType<JacocoReport>() })

    subprojects {
        val subproject = this
        subproject.plugins.withType<JacocoPlugin>().configureEach {
            subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }
                    .configureEach {
                        val testTask = this
                        val sourceSet = subproject.sourceSets.main.get()
                        sourceSets(sourceSet)
                        executionData(testTask)
                    }

            subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.forEach {
                rootProject.tasks["codeCoverageReport"].dependsOn(it)
            }
        }
    }

    reports {
        xml.isEnabled = false
        html.isEnabled = true
    }
}

tasks.check {
    dependsOn(detektAll)
    finalizedBy("codeCoverageReport")
}
