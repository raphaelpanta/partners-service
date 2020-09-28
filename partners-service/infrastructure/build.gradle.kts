plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("de.grundid.opendatalab:geojson-jackson:1.14")
    implementation(project(":partners-service:domain"))
}