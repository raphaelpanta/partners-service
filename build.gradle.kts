plugins {
    base
    kotlin("jvm") version "1.4.10"
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
