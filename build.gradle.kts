plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

group = "com.github.caay2000"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<Wrapper> {
    gradleVersion = "7.5"
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}
