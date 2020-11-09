plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(kotlin("stdlib", Libs.Kotlin.version))
    implementation(Libs.Kotlin.coroutinesCore)
}