import groovy.json.JsonSlurper
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.net.URL

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.protobuf") version "0.9.5"
}

android {
    namespace = "net.youapps.calcyou"
    compileSdk = 36

    defaultConfig {
        applicationId = "net.youapps.calcyou"
        minSdk = 23
        targetSdk = 35
        versionCode = 7
        versionName = "4.0"
        // TODO: update 'currentApiVersion' below before each release

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val currencyApiVersion = "2026-02-18"
// update the list of currencies if it doesn't exist yet
tasks.register("updateCurrencies") {
    val currenciesPath = File("app/src/main/res/raw/currencies.csv")
    if (currenciesPath.exists()) return@register

    if (!currenciesPath.parentFile.exists()) {
        currenciesPath.parentFile.mkdirs()
    }

    val baseCurrency = "eur" // get exchange rates from euro to everything else
    val baseUrl = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@${currencyApiVersion}/v1"
    @Suppress("UNCHECKED_CAST")
    val currencyNames = JsonSlurper().parse(URL("$baseUrl/currencies.json")) as Map<String, String> // Map<CurrencyCode, CurrencyName>
    @Suppress("UNCHECKED_CAST")
    val ratesResp = JsonSlurper().parse(URL("$baseUrl/currencies/$baseCurrency.json")) as Map<String, Any>
    @Suppress("UNCHECKED_CAST")
    val rates = ratesResp["eur"] as Map<String, Number> // Map<CurrencyCode, ExchangeRate>

    println("Succesfully fetched currencies.")

    var currencyCsv = "Euro,1.0\n"
    for ((currencyCode, currencyName) in currencyNames) {
        if (currencyCode == baseCurrency || currencyName.isBlank()) continue

        rates[currencyCode]?.let { exchangeRate ->
            currencyCsv += "$currencyName,${exchangeRate.toDouble()}\n"
        }
    }

    currenciesPath.outputStream().bufferedWriter().use { writer ->
        writer.write(currencyCsv)
    }
}

tasks.preBuild {
    dependsOn(tasks.getByName("updateCurrencies"))
}

dependencies {

    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation(platform("androidx.compose:compose-bom:2026.03.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.navigation:navigation-compose:2.9.7")

    // Data persistence
    implementation("androidx.datastore:datastore:1.2.1")
    implementation("com.google.protobuf:protobuf-kotlin-lite:4.34.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.32.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
                create("kotlin")
            }
        }
    }
}