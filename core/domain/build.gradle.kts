plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.jefisu.domain"

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = false
    }
}