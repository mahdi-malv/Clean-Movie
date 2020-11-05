buildscript {
    val kotlin_version by extra("1.4.10")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Libs.Gradle.version}")
        classpath(kotlin("gradle-plugin", version = Libs.Kotlin.version))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}