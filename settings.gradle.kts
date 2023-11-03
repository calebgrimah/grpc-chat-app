pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        google()
        mavenLocal()
        mavenCentral()
    }

}
rootProject.name = "chat-app"
include("data")
