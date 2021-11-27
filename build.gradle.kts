plugins {
    kotlin("jvm") version "1.5.30-RC"
    application
}

application {
    mainClass.set("com.bentoboxen.MainKt")
}

group = "com.bentoboxen"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions { jvmTarget = "14" } }
    distTar { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
    distZip { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("dev.kord:kord-core:0.8.0-M5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("com.sedmelluq:lavaplayer:1.3.73")
    implementation("aws.sdk.kotlin:s3:0.9.3-alpha")
    implementation("io.github.microutils:kotlin-logging:2.0.11")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.14.1")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
}
