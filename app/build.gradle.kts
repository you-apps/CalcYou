import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import java.net.URL

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "net.youapps.calcyou"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.youapps.calcyou"
        minSdk = 21
        targetSdk = 34
        versionCode = 4
        versionName = "2.0"

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
    kotlinOptions {
        jvmTarget = "17"
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

// update the list of currencies if it doesn't exist yet
tasks.register("updateCurrencies") {
    val currenciesPath = File("app/src/main/res/raw/currencies.csv")
    if (currenciesPath.exists()) return@register

    val baseCurrency = "eur" // get exchange rates from euro to everything else
    val currencyNames = JsonSlurper().parse(URL("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json")) as Map<String, String> // Map<CurrencyCode, CurrencyName>
    val ratesResp = JsonSlurper().parse(URL("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/$baseCurrency.json")) as Map<String, Any>
    val rates = ratesResp["eur"] as Map<String, Number> // Map<CurrencyCode, ExchangeRate>

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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
