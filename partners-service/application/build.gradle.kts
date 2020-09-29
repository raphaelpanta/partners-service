plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("de.grundid.opendatalab:geojson-jackson:1.14")
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.9")
    implementation("io.konform:konform-jvm:0.2.0")
    implementation(project(":partners-service:domain"))
}