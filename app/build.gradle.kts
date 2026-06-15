plugins {
    id("java")
    checkstyle
    jacoco
    id("org.sonarqube") version "7.3.1.8318"
}


group = "hexlet.code"
version = "1.0-SNAPSHOT"
val junitVersion = "5.10.0"
val assertjVersion = "3.27.7"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation ("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


checkstyle {
    configProperties["org.checkstyle.google.suppressionfilter.config"] =
        "${project.rootDir}/config/checkstyle/checkstyle-suppressions.xml"
}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}


sonarqube {
  properties {
    property("sonar.projectKey", "KostKhar_qa-auto-engineer-java-project-78")
    property("sonar.organization", "kostkhar")
  }
}