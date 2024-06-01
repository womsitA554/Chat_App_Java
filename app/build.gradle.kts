plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.chat_app_java"
    compileSdk = 34
    packagingOptions{
        exclude("META-INF/DEPENDENCIES")
    }

    defaultConfig {
        applicationId = "com.example.chat_app_java"
        minSdk = 26
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.hbb20:ccp:2.5.0")

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
}