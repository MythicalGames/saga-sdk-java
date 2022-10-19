plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")
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

val user: String? = project.findProperty("gprUser") as String? ?: System.getenv("USERNAME")
val token: String? = project.findProperty("gprKey") as String? ?: System.getenv("TOKEN")

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MythicalGames/saga-java-sdk")
            credentials {
                username = user
                password = token
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            group = rootProject.group
            version = rootProject.version as String

            pom {
                name.set("Saga SDK")
                description.set("Saga SDK for Java game servers")
                url.set("https://mythicalgames.com/")

                scm {
                    connection.set("https://github.com/MythicalGames/saga-sdk-java.git")
                    developerConnection.set("https://github.com/MythicalGames/saga-sdk-java.git")
                    url.set("https://github.com/MythicalGames/saga-sdk-java")
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        organization.set("Mythical Games")
                        organizationUrl.set("https://mythicalgames.com/")
                    }
                }
            }
        }
    }
}

tasks.withType<Sign> {
    onlyIf { project.hasProperty("signingKey") && project.hasProperty("signingPassword") }
}

signing {
    useInMemoryPgpKeys(project.findProperty("signingKey") as String?, project.findProperty("signingPassword") as String?)
    sign(publishing.publications["mavenJava"])
}