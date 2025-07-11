pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "KotlinApp"
include(":app")
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":feature:auth")
include(":feature:home")
include(":feature:profile") 