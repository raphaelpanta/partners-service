import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "com.github.raphaelpanta.partners.MainKt"
}

sourceSets {
    create("endToEndTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/endToEndTest/kotlin")
            resources.srcDir("src/endToEndTest/resources")
            compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

val endToEndTestTask = task<Test>("endToEndTest") {
    description = "Runs end to end test for partners-service"
    group = "verification"
    testClassesDirs = sourceSets["endToEndTest"].output.classesDirs
    classpath = sourceSets["endToEndTest"].runtimeClasspath
    mustRunAfter(tasks["test"])
    useJUnitPlatform()
}

val endToEndTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.core:jackson-core:2.11.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
    implementation("io.javalin:javalin:3.10.1")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("org.litote.kmongo:kmongo:4.1.2")
    implementation(project(":partners-service:application"))
    implementation(project(":partners-service:domain"))
    implementation(project(":partners-service:infrastructure"))
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.9")

    testImplementation("io.mockk:mockk:1.10.2")
    endToEndTestImplementation("io.cucumber:cucumber-java8:6.8.0")
    endToEndTestImplementation("io.cucumber:cucumber-junit-platform-engine:6.8.0")
    endToEndTestImplementation("io.ktor:ktor-client-cio-jvm:1.4.1")
    endToEndTestImplementation("io.ktor:ktor-client-jackson:1.4.1")
    endToEndTestImplementation("io.ktor:ktor-client-serialization-jvm:1.4.1")
    endToEndTestImplementation("io.ktor:ktor-client-logging-jvm:1.4.1")
    endToEndTestImplementation("org.testcontainers:testcontainers:1.14.3")
    endToEndTestImplementation("org.testcontainers:mongodb:1.14.3")
    endToEndTestImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
}

tasks.check { dependsOn(endToEndTestTask) }
