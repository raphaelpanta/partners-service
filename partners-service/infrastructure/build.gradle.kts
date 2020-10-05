plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":partners-service:domain"))
    implementation("org.litote.kmongo:kmongo:4.1.2")
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.9")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
}
