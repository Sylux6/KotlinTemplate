val atriumVersion = "0.12.0"
val kotlinLoggingVersion = "1.8.3"
val kotlinVersion = "1.3.72"
val logbackVersion = "1.2.3"
val mockkVersion = "1.10.0"
val spekVersion = "2.0.12"

plugins {
    application
    java
    `maven-publish`
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.jmailen.kotlinter") version "2.4.1"
}

application {
    mainClassName = "com.github.sylux6.kotlinjvmtemplate.AppKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // Core
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    // Test
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:$atriumVersion")
}

tasks {
    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        archiveFileName.set("MyApp.jar")
        manifest {
            attributes["Main-Class"] = "com.github.sylux6.kotlinjvmtemplate.AppKt"
        }
        // Exclude dependencies if there are ClassNotFoundError with them
        minimize {
            exclude(dependency("ch.qos.logback:logback-classic"))
        }
    }
}
