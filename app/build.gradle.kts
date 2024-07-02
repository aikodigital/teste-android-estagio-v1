import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "br.com.samuel.testeaiko"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.samuel.testeaiko"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "OLHO_VIVO_API_KEY", "\"${properties.getProperty("OLHO_VIVO_API_KEY")}\"")
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
        // Desugaring para utilizar time apis java 8
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        // Desabilitar warnings material design 3 no compose
        freeCompilerArgs = listOf("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
    }

    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.android.material)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.constraint.layout)
    implementation(libs.androidx.compose.lifecycle.viewmodel)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.androidx.compose.icons.extended)

    implementation(libs.retrofit)
    implementation(libs.retrofit.jackson)
    implementation(libs.jackson.datatypes)
    implementation(libs.osm.library)

    implementation(libs.hilt.library)
    kapt(libs.hilt.android.compiler)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.junit)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    kaptAndroidTest(libs.hilt.android.compiler)
}
