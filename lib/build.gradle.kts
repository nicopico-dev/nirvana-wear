plugins {
    kotlin("jvm") version "1.8.10"
    `java-library`
    kotlin("plugin.serialization") version "1.8.10"
}

repositories {
    mavenCentral()
}

dependencies {
    val ktor_version = "2.2.4"
    val kotest_version = "5.5.5"

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
