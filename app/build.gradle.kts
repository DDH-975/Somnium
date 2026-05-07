import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

val properties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    properties.load(FileInputStream(localPropertiesFile))
} else {
    throw GradleException("local.properties 파일이 없거나 잘못된 위치에 있습니다.")
}


val apiKey: String = properties["Apikey"] as String? ?: throw GradleException("local.properties 파일에 Apikey가 정의되지 않았습니다.")

android {
    namespace = "com.project.somnium"
    compileSdk = 35

    viewBinding.isEnabled = true

    defaultConfig {
        applicationId = "com.project.somnium"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "Apikey", "\"$apiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity-ktx:1.8.x")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt("com.github.bumptech.glide:compiler:4.15.1")
    kapt("androidx.room:room-compiler:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}