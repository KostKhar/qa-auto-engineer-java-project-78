plugins {
    id("java")
    checkstyle
    jacoco
     id ("org.sonarqube") version ("7.3.0.8198")
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
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
                   ("org.junit.jupiter:junit-jupiter")
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


sonar {
  properties {
    property("sonar.projectKey", "cicdpiplinetohell_qa-auto-engineer-java-project-71")
    property("sonar.organization", "cicdpiplinetohell")
    property("sonar.coverage.jacoco.xmlReportPaths", "${layout.buildDirectory.get()}/reports/jacoco/test/jacocoTestReport.xml")
  }
}