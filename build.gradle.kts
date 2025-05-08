plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.borovkov.egor"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("net.datafaker:datafaker:2.4.3")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	implementation("org.slf4j:slf4j-api:2.0.17")
	implementation("org.flywaydb:flyway-core:11.8.0")
	runtimeOnly("org.flywaydb:flyway-database-postgresql:11.8.0")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation ("org.testcontainers:junit-jupiter")
	testImplementation ("org.testcontainers:postgresql")
	testImplementation ("org.springframework.boot:spring-boot-testcontainers")
	testImplementation ("org.testcontainers:testcontainers")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
