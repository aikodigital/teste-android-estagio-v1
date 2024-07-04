plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.safe.args)
}

secrets {
    propertiesFileName = "credentials.properties"

    defaultPropertiesFileName = "local.properties"

    ignoreList.add("keyToIgnore")
    ignoreList.add("sdk.*")
}

android {
    namespace = "com.exemple.urbanbus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.exemple.urbanbus"
        minSdk = 24
        targetSdk = 34
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.play.service.maps)
    implementation(libs.play.service.location)

    implementation(libs.androidx.nav.fragment)
    implementation(libs.androidx.nav.ui)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}