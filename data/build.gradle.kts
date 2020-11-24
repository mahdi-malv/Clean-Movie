import com.google.protobuf.gradle.*

plugins {
    id("com.google.protobuf") version "0.8.12"
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(Versions.Build.minSdk)
        targetSdkVersion(Versions.Build.targetSdk)
        versionCode = Versions.versionCode
        versionName = Versions.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release"){
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", Libs.Kotlin.version))
    implementation(Libs.Kotlin.coroutinesCore)
    implementation(Libs.Kotlin.coroutinesAndroid)

    // AndroidX
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.material)

    implementation(project(":domain"))

    // Network
    implementation(Libs.Network.Retrofit.retrofit)
    implementation(Libs.Network.Retrofit.converterMoshi)
    implementation(Libs.Network.OkHttp.okHttp)
    implementation(Libs.Network.OkHttp.logging)
    implementation(Libs.Network.Moshi.moshi)
    kapt(Libs.Network.Moshi.codeGen)

    // Storage
    implementation(Libs.Room.ktx)
    implementation(Libs.Room.runtime)
    kapt(Libs.Room.compiler)
    implementation(Libs.AndroidX.DataStore.typed)
    implementation(Libs.AndroidX.DataStore.protobuf)


    // Test
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.espresso)
    androidTestImplementation(Libs.Test.testExt)
    androidTestImplementation(Libs.Network.OkHttp.mockServer)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins{
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

