plugins {
    java
    application
    id ("org.springframework.boot") version "3.0.0"
    id ("io.spring.dependency-management") version "1.1.0"
    id ("io.freefair.lombok") version "6.4.1"
}

group "org.ocm"
version "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.json:json:20220320")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:2.1.214")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}