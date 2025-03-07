import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("kapt") version "2.1.10"
    kotlin("plugin.spring") version "2.1.10" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "2.1.10" apply false
    id("org.jetbrains.kotlin.plugin.noarg") version "2.1.10"
    id("org.jetbrains.kotlin.plugin.allopen")  version "2.1.10"
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7"


}

allprojects {
    repositories {
        mavenCentral()
    }

}

subprojects {
    apply {
        plugin("kotlin")
        plugin("idea")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("java-library")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("org.jetbrains.kotlin.plugin.allopen")
    }

    group = "com.search"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    extra["springCloudVersion"] = "2024.0.0"

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.github.microutils:kotlin-logging:3.0.5")

        testImplementation("com.ninja-squad:springmockk:4.0.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
        testImplementation("io.kotest:kotest-assertions-core:5.9.1")
        testImplementation("io.mockk:mockk:1.13.13")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")



        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    kotlin {
        jvmToolchain {
            languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_21.toString())
        }
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    noArg {
        invokeInitializers = true
    }

    val jar: Jar by tasks
    jar.enabled = false

    val bootJar: BootJar by tasks
    bootJar.enabled = false

}

