import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")

    id("com.google.protobuf") version "0.9.1"

    kotlin("jvm") version "1.7.20"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("io.grpc:grpc-netty:${Versions.grpc}")
    api("io.grpc:grpc-protobuf:${Versions.grpc}")
    api("io.grpc:grpc-stub:${Versions.grpc}")
    api("io.grpc:grpc-services:${Versions.grpc}")
    api("io.grpc:grpc-kotlin-stub:${Versions.grpcKotlin}")

    api("com.google.protobuf:protobuf-kotlin:${Versions.protobuf}")
    api("com.google.protobuf:protobuf-java-util:${Versions.protobuf}")

    implementation("javax.annotation:javax.annotation-api:${Versions.javaxAnnotation}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withSourcesJar()
    withJavadocJar()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val protoDocType: String by project
val protoDocFile: String by project

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${Versions.protobuf}"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${Versions.grpc}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${Versions.grpcKotlin}:jdk7@jar"
        }
        id("kotlin")
        id("doc") {
            artifact = "io.github.pseudomuto:protoc-gen-doc:${Versions.protocGenDoc}"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
                id("kotlin")
                id("doc") {
                    option("${protoDocType},${protoDocFile}")
                }
            }
        }
    }
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