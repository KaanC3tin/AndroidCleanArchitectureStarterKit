import com.android.build.gradle.internal.cxx.configure.createNativeBuildSystemVariantConfig
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

val SERVER_URL: String = project.findProperty("SERVER_URL") as String
val BASE_URL: String = project.findProperty("BASE_URL") as String

android {
    namespace = "com.karegraf.androidarchitecturestarterkit"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.karegraf.androidarchitecturestarterkit"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SERVER_URL", "\"$SERVER_URL\"")
        buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
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
        compose = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

val generateNetworkSecurityConfig by tasks.registering {
    val serverUrl = project.findProperty("SERVER_URL") ?: project.findProperty("SERVER_URL") ?: "127.0.0.1"
    val templateFile = file("src/main/res/xml/network_security_config_template.xml")
    val configFile = file("src/main/res/xml/network_security_config.xml")

    doLast {
        val content = templateFile.readText()
        val updatedContent = content.replace("SERVER_URL", serverUrl.toString())
        configFile.writeText(updatedContent)
    }
}

tasks.named("preBuild") { dependsOn(generateNetworkSecurityConfig) }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.0")

}
