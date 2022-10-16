import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
    id("idea")
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.google.protobuf")

    kotlin("jvm")
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