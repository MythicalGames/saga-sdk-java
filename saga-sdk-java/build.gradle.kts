plugins {
    id("idea")
    id("java")
    id("java-library")
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api(project(":saga-sdk-proto"))
    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    implementation("org.apache.commons:commons-lang3:${Versions.commonsLang}")
    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")
    implementation("games.mythical:proto-util:0.2.2-alpha")
    implementation("org.projectlombok:lombok:${Versions.lombok}")

    annotationProcessor("org.projectlombok:lombok:${Versions.lombok}")

    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.junit}")
    testImplementation("org.slf4j:slf4j-simple:${Versions.slf4j}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockito}")

    testAnnotationProcessor("org.projectlombok:lombok:${Versions.lombok}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withSourcesJar()
    withJavadocJar()
}