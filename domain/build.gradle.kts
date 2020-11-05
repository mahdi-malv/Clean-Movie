import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(Libs.Kotlin.coroutinesCore)
}