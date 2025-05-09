plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.lv1_a"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lv1_a"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources {
        excludes += listOf(
         "META-INF/INDEX.LIST", "META-INF/LICENSE",
         "META-INF/LICENSE.txt", "META-INF/NOTICE",
         "META-INF/NOTICE.txt", "META-INF/io.netty.versions.properties"
        )
        }
    }


}







dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    // HiveMQ MQTT Client dependency
    implementation(libs.hivemq.mqtt.client)

}