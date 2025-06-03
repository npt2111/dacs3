plugins {

   alias(libs.plugins.androidApplication)

    id("com.google.gms.google-services")



}

android {
    namespace = "com.example.dacs3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dacs3"
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
//        dataBinding{
//            enable = true;
//        }
//        viewBinding{
//            enable=true;
//        }
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
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.bumptech.glide:glide:3.7.0")


    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


    //RX java
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    testImplementation("junit:junit:4.13.2")
    //
    implementation("com.google.firebase:firebase-database")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    //
    implementation("com.google.firebase:firebase-messaging")

    implementation("com.google.firebase:firebase-storage")

    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("com.orhanobut:dialogplus:1.11@aar")
    implementation ("com.codesgood:justifiedtextview:1.1.0")

}
