 object Libs {

     // region global
     object Kotlin {
         const val version = "1.4.10"
         const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"
         const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"
     }

     object Gradle {
         const val plugin = "com.android.tools.build:gradle:4.2.0-alpha16"
     }
     // endregion

     // region Libraries
     object Maven {
         const val jitPack = "https://jitpack.io"
     }

     object AndroidX {
         const val core = "androidx.core:core-ktx:1.3.2"
         const val multiDex = "androidx.multidex:multidex:2.0.1"
         const val appCompat = "androidx.appcompat:appcompat:1.2.0"
         const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
         const val material = "com.google.android.material:material:1.2.1"
         const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01"
         const val cardView = "androidx.cardview:cardview:1.0.0"
         const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
         const val media = "androidx.media:media:1.1.0"
         const val exIfInterface = "androidx.exifinterface:exifinterface:1.2.0"
         const val vectorDrawable = "androidx.vectordrawable:vectordrawable-animated:1.1.0"

         object Navigation {
             const val compose = "androidx.navigation:navigation-compose:1.0.0-alpha01"
         }

         object Hilt {
             val android = "com.google.dagger:hilt-android:2.28-alpha"
             val lifeCycle = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"
             val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.28-alpha"
             val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha02"
             val gradle = "com.google.dagger:hilt-android-gradle-plugin:2.28-alpha"
         }

         object Compose {
             const val version = "1.0.0-alpha06"
             const val ui = "androidx.compose.ui:ui:$version"
             const val material = "androidx.compose.material:material:$version"
             const val tooling = "androidx.ui:ui-tooling:$version"
             const val animation = "androidx.compose.animation:animation:$version"
             const val iconExt = "androidx.compose.material:material-icons-extended:$version"

             object Ext {
                 const val coilImage = "jp.wasabeef.composable:coil:1.0.1"
             }
         }

         object Test {
             const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
             const val junitExt = "androidx.test.ext:junit:1.1.1"
         }
     }

     object Utils {
         const val markdown = "com.github.mukeshsolanki:MarkdownView-Android:1.0.6"
     }

     object Google {
         const val material = "com.google.android.material:material:1.2.0"

         object Firebase {
             const val core = "com.google.firebase:firebase-core:17.5.0"
             const val iid = "com.google.firebase:firebase-iid:20.2.4"
             const val messaging = "com.google.firebase:firebase-messaging:20.2.4"
             const val analytics = "com.google.firebase:firebase-analytics-ktx:17.5.0"
             const val perf = "com.google.firebase:firebase-perf:19.0.8"
         }
     }

     object Network {
         object Retrofit {
             const val retrofit = "com.squareup.retrofit2:retrofit:2.6.4"
             const val rxJava2 = "com.squareup.retrofit2:adapter-rxjava2:2.5.0"
             const val converterGson = "com.squareup.retrofit2:converter-gson:2.6.4"
             const val converterMoshi = "com.squareup.retrofit2:converter-moshi:2.6.4"
             const val converterScalars = "com.squareup.retrofit2:converter-scalars:2.5.0"
         }

         object OkHttp {
             const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.12.12"
             const val okHttp = "com.squareup.okhttp3:okhttp:4.9.0"
             const val mockServer = "com.squareup.okhttp3:mockwebserver::4.9.0"
         }

         object Moshi {
             const val moshi = "com.squareup.moshi:moshi:1.11.0"
             const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:1.11.0"
         }
     }

     object Room {
         const val runtime = "androidx.room:room-runtime:2.2.5"
         const val rxJava2 = "androidx.room:room-rxjava2:2.2.5"
         const val ktx = "androidx.room:room-ktx:2.2.5"
         const val compiler = "androidx.room:room-compiler:2.2.5"
     }

     object WorkManager {
         const val runtime = "androidx.work:work-runtime-ktx:2.4.0"
         const val rxJava2 = "androidx.work:work-rxjava2:2.4.0"
     }

     object Font {
         const val calligraphy3 = "io.github.inflationx:calligraphy3:3.1.1"
         const val viewPump = "io.github.inflationx:viewpump:2.0.3"
     }

     object Glide {
         const val core = "com.github.bumptech.glide:glide:4.11.0"
         const val coreCompiler = "com.github.bumptech.glide:compiler:4.9.0"
     }


     object Rx {
         const val rxJava2 = "io.reactivex.rxjava2:rxjava:2.2.19"
         const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"
     }

     object Logging {
         const val pulp = "ir.malv.utils:pulp:0.2.0"
     }

     object Test {
         const val junit = "junit:junit:4.13.1"
         const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
         const val testExt = "androidx.test.ext:junit:1.1.2"
     }
     // endregion

 }