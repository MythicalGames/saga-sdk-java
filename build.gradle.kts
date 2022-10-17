plugins {
    id("pl.allegro.tech.build.axion-release") version "1.14.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
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

nexusPublishing {
    repositories {
        sonatype()
    }
}