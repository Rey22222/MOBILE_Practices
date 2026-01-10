plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.mirea.khasanova.succuforest"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.mirea.khasanova.succuforest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-auth")
    val room_version = "2.7.0-alpha13"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-common:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.picasso:picasso:2.8'
    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("com.github.bumptech.glide:compiler:4.16.0")
    implementation 'com.squareup.retrofit2:converter-gson:2.11.0'
    implementation 'com.squareup.retrofit2:retrofit:2.11.0'
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.navigation:navigation-fragment:2.8.5")
    implementation("androidx.navigation:navigation-ui:2.8.5")
}