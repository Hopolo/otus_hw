import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow")
}

val guava: String by project;

dependencies {
    implementation("com.google.guava:guava:$guava")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("gradleHelloWorld")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.HelloOtus"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
