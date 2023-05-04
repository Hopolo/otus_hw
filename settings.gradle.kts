rootProject.name = "otus_hw"
include("hw01-gradle")
include("hw03-test-framework")
include("hw05-autologging")
include("hw06-atm")

pluginManagement {
    val johnrengelmanShadow: String by settings

    plugins {
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
    }
}
