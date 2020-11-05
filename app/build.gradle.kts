import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
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

    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
        kotlinCompilerVersion = Libs.Kotlin.version
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
    implementation(Libs.AndroidX.lifecycle)

    // Compose
    implementation(Libs.AndroidX.Compose.animation)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.iconExt)
    implementation(Libs.AndroidX.Compose.Ext.coilImage)

    // Navigation
    implementation(Libs.AndroidX.Navigation.compose)

    // Local
    implementation(project(":domain"))
    implementation(project(":data"))

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