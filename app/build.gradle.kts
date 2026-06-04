import com.android.build.api.dsl.Packaging
import com.android.tools.r8.internal.de

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.dine_discover"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dine_discover"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //for api:
        buildConfigField("String", "API_KEY", "\"08888331efmshcfb1814cbdadc2ap18eb0bjsne10862f12c4c\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            android.buildFeatures.buildConfig = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        viewBinding = true
    }
    packagingOptions{
        exclude("META-INF/ASL-2.0.txt")
        exclude("META-INF/LGPL-3.0.txt")
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
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
  //  implementation("org.testng:testng:6.9.6")
 //   implementation("junit:junit:4.12")
  //  implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(libs.junit)
    //testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //dependencies for firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.google.firebase.analytics)
    implementation(libs.firebase.firestore)

    //dependencies for API
    implementation(libs.retrofit)        // retrofit for HTTP requests
    implementation(libs.converter.gson)  // sson converter to parse JSON responses
    implementation(libs.logging.interceptor)  // logging interceptor for debugging network requests

    // Declare the dependency for the Cloud Firestore library
    implementation (libs.gson)

    // dependency for card layout
    implementation(libs.cardview)

    // dependency for sending email
    implementation ("com.sun.mail:android-mail:1.6.0")
    implementation ("com.sun.mail:android-activation:1.6.0")


}

