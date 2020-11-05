import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "ir.malv.cleanmovies"
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
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
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
    implementation(Libs.Network.OkHttp.loggingInterceptor)
    implementation(Libs.Network.OkHttp.okHttp)
    implementation(Libs.Network.Moshi.moshi)
    kapt(Libs.Network.Moshi.codeGen)

    // Database
    implementation(Libs.Room.ktx)
    implementation(Libs.Room.runtime)
    kapt(Libs.Room.compiler)

    // Test
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.Test.espresso)
    androidTestImplementation(Libs.Test.testExt)
    androidTestImplementation(Libs.Network.OkHttp.mockServer)
}