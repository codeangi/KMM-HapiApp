plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.deepak.myapplication.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.deepak.myapplication.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val koinVersion = "3.3.3"
    val koinCompose = "3.4.2"
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation ("com.airbnb.android:lottie-compose:4.0.0")
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")


    //Koin
    implementation("io.insert-koin:koin-android:$koinVersion")
    /*implementation("io.insert-koin:koin-androidx-navigation:$koinAndroid")*/
    implementation("io.insert-koin:koin-androidx-compose:$koinCompose")
}