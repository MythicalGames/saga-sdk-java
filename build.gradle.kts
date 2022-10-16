plugins {
    id("idea")
    id("java")
    id("java-library")
    id("maven-publish")

    id("com.google.protobuf") version "0.9.1" //apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false

    id("pl.allegro.tech.build.axion-release") version "1.14.0"
}

scmVersion {
    tag {
        prefix.set("") // prefix to be used, 'v' by default, empty String means no prefix
    }
    useHighestVersion.set(true)
    ignoreUncommittedChanges.set(false)
    hooks {
        val pattern = KotlinClosure2({ currentVersion: String, c: pl.allegro.tech.build.axion.release.domain.hooks.HookContext ->
            "version: $currentVersion"
        })
        val replacement = KotlinClosure2({ newVersion: String, c: pl.allegro.tech.build.axion.release.domain.hooks.HookContext ->
            "version: $newVersion"
        })
        pre("fileUpdate", mapOf(
            "file" to "README.md",
            "pattern" to pattern,
            "replacement" to replacement
        ))
        pre("commit")
        post("push")
    }
    checks {
        uncommittedChanges.set(false)
    }
}

group = "games.mythical"
version = scmVersion.version

