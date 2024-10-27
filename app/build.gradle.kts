plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Room
    alias(libs.plugins.androidx.room)

    // KSP
    alias(libs.plugins.devtools.ksp)

    // Secrets
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.ergegananputra.aplikasikpu"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ergegananputra.aplikasikpu"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    namespace = "com.ergegananputra.aplikasikpu"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.location)
    implementation(libs.firebase.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // compose viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // [Coil](https://github.com/coil-kt/coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.paging)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Maps
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose.utils)
    implementation(libs.maps.compose.widgets)

    // Permission
    implementation(libs.accompanist.permissions)
}

// 2. Optionally configure the plugin
secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"

    // Add keys that the plugin should ignore from the properties file
    ignoreList.add("keyToIgnore")
    ignoreList.add("ignore*")
}