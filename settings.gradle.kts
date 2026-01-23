pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("com\\.google\\.devtools.*")
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

rootProject.name = "GitHub Profile Explorer"
include(":app")
include(":core:di")
include(":core:network")
include(":core:domain")
include(":core:data")
include(":core:common")
include(":feature:userRepos")
include(":feature:userProfile")
include(":core:ui")
include(":feature:userRepoDetail")
