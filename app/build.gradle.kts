plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.bendiyinyue1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bendiyinyue1"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.13.0")
    compileOnly ("org.projectlombok:lombok:1.18.24") // 使用最新版本号
    annotationProcessor ("org.projectlombok:lombok:1.18.24")

    implementation ("com.google.code.gson:gson:2.8.6")
    //noinspection UseTomlInstead,UseTomlInstead
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //数据解析
    //noinspection GradleDependency
    implementation (libs.google.gson)
    //图片加载
    //noinspection UseTomlInstead
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //网络请求
    //noinspection UseTomlInstead
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")


    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}
