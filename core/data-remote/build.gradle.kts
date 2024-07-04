plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.secret.gradle.plugin)
}

android {
    namespace = "com.jefisu.data_remote"

    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = false
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.cookie.store.okhttp)

    implementation(projects.core.domain)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}