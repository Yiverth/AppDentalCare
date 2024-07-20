plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.yiverthdevs.dentalcare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yiverthdevs.dentalcare"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion="1.5.14"
    }
}
dependencies {

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation("androidx.core:core-ktx:1.9.24")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //koin
    implementation("io.insert-koin:koin-android:3.2.0")
    //lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    //kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage")

    //jetpack compose
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.material:material")                     // MaterialDesign2
    implementation("androidx.compose.ui:ui-tooling-preview")                 // Previsualizar composables
    debugImplementation("androidx.compose.ui:ui-tooling")                    // Funcionalidades avazadas de depuración
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")          // Realizar pruebas unitarias a tus composables
    debugImplementation("androidx.compose.ui:ui-test-manifest")              // funcionalidades avanzadas de pruebas unitarias
    implementation("androidx.activity:activity-compose:1.9.0")               // Para integrar Compose con actividades de Android
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")   // Para usar ViewModels con Compose
    implementation("androidx.compose.runtime:runtime-livedata")              // Para integrar LiveData con Compose
    implementation("androidx.compose.runtime:runtime-rxjava2")               // Para integrar RxJava con Compose

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}