import com.google.protobuf.gradle.id
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.0"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.jetbrains.compose") version "1.3.0"
    id ("com.google.protobuf") version "0.9.2"
    id ("idea")
    id ("java")
}

group = "ng.thenorthstar"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
    google()
    mavenLocal()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

val daggerVersion by extra("2.45")

dependencies {
    implementation(compose.desktop.currentOs)

    // Module dependencies
    implementation(project(":data"))

    // Dagger : A fast dependency injector for Android and Java.
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
//    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.3.0")
    kaptTest("com.google.dagger:dagger-compiler:$daggerVersion")

    // Cyclone : https://github.com/theapache64/cyclone
    implementation("com.theapache64:cyclone:1.0.0-alpha01")

    // Decompose : Decompose
    val decomposeVersion = "0.2.5"
    implementation("com.arkivanov.decompose:decompose-jvm:$decomposeVersion")
    implementation("com.arkivanov.decompose:extensions-compose-jetbrains-jvm:$decomposeVersion")

    implementation ("com.google.protobuf:protobuf-gradle-plugin:0.8.17")
    implementation ("com.google.protobuf:protobuf-java:3.22.0")
    implementation ("com.google.protobuf:protobuf-java-util:3.22.0")
    implementation ("io.grpc:grpc-netty-shaded:1.53.0")
    implementation ("io.grpc:grpc-protobuf:1.53.0")
    implementation ("io.grpc:grpc-stub:1.53.0")
    implementation ("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation ("com.google.protobuf:protobuf-kotlin:3.22.0")

    implementation ("io.grpc:protoc-gen-grpc-kotlin:1.0.0")
    implementation ("com.google.protobuf:protobuf-lite:3.0.0")

    /**
     * Testing Dependencies
     */
    testImplementation("org.mockito:mockito-inline:3.7.7")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // DaggerMock
    testImplementation("com.github.fabioCollini.daggermock:daggermock:0.8.5")
    testImplementation("com.github.fabioCollini.daggermock:daggermock-kotlin:0.8.5")

    // Mockito Core : Mockito mock objects library core API and implementation
    testImplementation("org.mockito:mockito-core:3.7.7")

    // Expekt : An assertion library for Kotlin
    testImplementation("com.github.theapache64:expekt:1.0.0")

    // JUnit

    // Kotlinx Coroutines Test : Coroutines support libraries for Kotlin
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
    testImplementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))

    // JUnit : JUnit is a unit testing framework for Java, created by Erich Gamma and Kent Beck.
    testImplementation(kotlin("test-junit5"))
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
}
protobuf {
    protoc {
        // You still need protoc like in the non-Android case
        artifact = ("com.google.protobuf:protoc:3.22.0")
    }

    plugins {
        id("grpc") {
            artifact = ("io.grpc:protoc-gen-grpc-java:1.39.0")
        }
        id("grpckt") {
            artifact = ("io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar")
        }

    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                 id("grpc")
                 id("grpckt")
            }
            task.builtins {
                // In most cases you don't need the full Java output
                // if you use the lite output.
                kotlin {}
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "ng.thenorthstar.AppKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "chat-app"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("src/main/resources/drawables")

            linux {
                iconFile.set(iconsRoot.resolve("launcher_icons/linux.png"))
            }

            windows {
                iconFile.set(iconsRoot.resolve("launcher_icons/windows.ico"))
            }

            macOS {
                iconFile.set(iconsRoot.resolve("launcher_icons/macos.icns"))
            }

        }
    }
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath ("com.google.protobuf:protobuf-gradle-plugin:0.8.17")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}