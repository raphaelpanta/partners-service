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

val endToEndTestTask = task<Test>("endToEndTest"){
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
    endToEndTestImplementation("io.cucumber:cucumber-java8:6.8.0")
    endToEndTestImplementation("io.cucumber:cucumber-junit-platform-engine:6.8.0")
}

tasks.check { dependsOn(endToEndTestTask)}