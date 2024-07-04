buildscript {
    dependencies {
        classpath (libs.google.google.services)
        classpath (libs.firebase.crashlytics.gradle.v302)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}




