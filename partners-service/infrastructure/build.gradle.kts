plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("de.grundid.opendatalab:geojson-jackson:1.14")
    implementation(project(":partners-service:domain"))
    implementation("org.litote.kmongo:kmongo:4.1.2")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
}