plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.diagramadorcompi1"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.diagramadorcompi1"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

tasks.register<JavaExec>("generateJflex") {
    description = "Generate JFlex lexer"
    group = "build"

    val jflexFile = file("src/main/java/com/example/diagramadorcompi1/Analizadores/Lexer.flex")
    val outputDir = file("src/main/java/com/example/diagramadorcompi1/Analizadores")

    inputs.file(jflexFile)
    outputs.dir(outputDir)

    mainClass.set("jflex.Main")
    classpath = configurations.maybeCreate("jflexClasspath")
    args = listOf("-d", outputDir.path, jflexFile.path)

    doFirst {
        outputDir.mkdirs()
    }
}

tasks.register<JavaExec>("generateCup") {
    description = "Generate CUP parser"
    group = "build"

    val cupFile = file("src/main/java/com/example/diagramadorcompi1/Analizadores/Parser.cup")
    val outputDir = file("src/main/java/com/example/diagramadorcompi1/Analizadores")

    inputs.file(cupFile)
    outputs.dir(outputDir)

    mainClass.set("java_cup.Main")
    classpath = configurations["cupClasspath"]

    args = listOf(
        "-destdir", outputDir.path,
        "-parser", "Parser",
        "-symbols", "sym",
        cupFile.path
    )

    doFirst {
        outputDir.mkdirs()
    }
}

configurations {
    create("jflexClasspath")
    create("cupClasspath")
}

dependencies {
    "jflexClasspath"("de.jflex:jflex:1.9.1")
    "cupClasspath"("com.github.vbmacher:java-cup:11b-20160615")
    compileOnly("com.github.vbmacher:java-cup:11b-20160615")
    runtimeOnly("com.github.vbmacher:java-cup:11b-20160615")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.benchmark.traceprocessor)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

tasks.named("generateJflex") {
    dependsOn("generateCup")
}

tasks.named("preBuild") {
    dependsOn("generateJflex")
}
