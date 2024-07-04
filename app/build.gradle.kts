import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}
val properties = Properties().apply {
    load(File("local.properties").inputStream())
}
val apiKey = properties["API_KEY"] as String
val apiUrl = properties["BASE_URL"] as String
android {
    namespace = "com.example.mapapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mapapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "BASE_URL", "\"$apiUrl\"")
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.dagger)

    implementation(libs.retrofit.v271)
    implementation(libs.converter.gson)
    implementation(libs.kotlin.stdlib)
    //faltou o androidx.core que esta no libs
    implementation(libs.androidx.appcompat.v131)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout.v213)
    //faltou o androidx.navigation que esta no libs

    implementation(libs.osmdroid.android)
    implementation(libs.directions.api.client)
    implementation(libs.gson)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}