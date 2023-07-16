plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

@Suppress("UnstableApiUsage")
android {
    compileSdk = 33

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.yassineabou.weather"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resourceConfigurations += listOf("en")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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
    namespace = "com.yassineabou.weather"
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // sdp
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    // ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    // dotsIndicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    // play-services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // coil
    implementation("io.coil-kt:coil:2.4.0")

    // room
    implementation("androidx.room:room-ktx:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

    // hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    // datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // kPermissions
    implementation("com.github.fondesa:kpermissions-coroutines:3.4.0")

    // networkX
    implementation("com.github.rommansabbir:NetworkX:4.2.0")
}
android {
    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
}
