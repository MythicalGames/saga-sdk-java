plugins {
    id("idea")
    id("java")
    id("java-library")
    id("maven-publish")

    id("com.google.protobuf") version "0.9.1" //apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false

    id("pl.allegro.tech.build.axion-release") version "1.14.0"
}

group = "games.mythical"
version = "0.5.34-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}