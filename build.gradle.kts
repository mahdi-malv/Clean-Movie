buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.Gradle.plugin)
        classpath(kotlin("gradle-plugin", version = Libs.Kotlin.version))
        classpath(Libs.AndroidX.Hilt.gradle)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}