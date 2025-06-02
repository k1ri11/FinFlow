pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FinFlow"
include(":app")
include(":feature:spending")
include(":core")
include(":uikit")
include(":feature:auth-impl")
include(":feature:profile")
include(":feature:friends-impl")
include(":feature:event")
include(":feature:auth-api")
include(":feature:friends-api")
