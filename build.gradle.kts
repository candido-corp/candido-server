plugins {
	// Core plugins
	id("java")
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"

	// SonarQube
	id("org.sonarqube") version "5.0.0.4638"

	// Flyway (official plugin)
	id("org.flywaydb.flyway") version "11.3.2"
	kotlin("jvm")
}

group = "com.candido"
version = "1.1.0"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

// ----------------------------------------------------------------------
// Dependency versions
// ----------------------------------------------------------------------
val postgresqlVersion = "42.7.5"
val flywayDBVersion = "11.3.2"
val jsonwebtokenVersion = "0.12.6"
val springdocOpenApiVersion = "2.3.0"
val passayVersion = "1.6.4"
val hibernateJpaModelGeneratorVersion = "6.5.2.Final"
val micrometerVersion = "1.14.4"

// Don't remove this block. You can use commands like ./gradlew flywayInfo
buildscript {
	dependencies {
		classpath("org.flywaydb:flyway-database-postgresql:11.3.2")
	}
}

// ----------------------------------------------------------------------
// Dependencies
// ----------------------------------------------------------------------
dependencies {
	// -- Spring Boot Starters (add your usual ones) --
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// -- Flyway and PostgreSQL Driver --
	// https://mvnrepository.com/artifact/org.flywaydb/flyway-core
	implementation("org.flywaydb:flyway-core:$flywayDBVersion")
	// https://mvnrepository.com/artifact/org.postgresql/postgresql
	implementation("org.postgresql:postgresql:$postgresqlVersion")
	// https://mvnrepository.com/artifact/org.flywaydb/flyway-database-postgresql
	implementation("org.flywaydb:flyway-database-postgresql:$flywayDBVersion")

	// -- JWT --
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
	implementation("io.jsonwebtoken:jjwt-api:$jsonwebtokenVersion")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jsonwebtokenVersion")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jsonwebtokenVersion")

	// -- Passay --
	// https://mvnrepository.com/artifact/org.passay/passay
	implementation("org.passay:passay:$passayVersion")

	// -- SpringDoc OpenAPI --
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenApiVersion")

	// -- Annotation Processors (Hibernate, Lombok, etc.) --
	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	annotationProcessor("org.projectlombok:lombok")
	// https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-jpamodelgen
	annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:$hibernateJpaModelGeneratorVersion")

	// -- Lombok (compileOnly) --
	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	compileOnly("org.projectlombok:lombok")

	// -- Profiler --
	// https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus
	implementation("io.micrometer:micrometer-registry-prometheus:$micrometerVersion")
	// https://mvnrepository.com/artifact/io.micrometer/micrometer-core
	implementation("io.micrometer:micrometer-core:$micrometerVersion")
	// https://mvnrepository.com/artifact/io.micrometer/micrometer-observation
	implementation("io.micrometer:micrometer-observation:$micrometerVersion")

	// -- Testing --
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("stdlib-jdk8"))
}

// ----------------------------------------------------------------------
// Flyway Configuration
// ----------------------------------------------------------------------
flyway {
	// Use your local DB or environment variables
	url = project.findProperty("DB_URL") as String?
	user = project.findProperty("DB_USER") as String?
	password = project.findProperty("DB_PASSWORD") as String?
	schemas = arrayOf(project.findProperty("DB_SCHEMA") as String?)
	locations = arrayOf(
		project.findProperty("FLYWAY_LOCATION_MIGRATION") as String?,
		project.findProperty("FLYWAY_LOCATION_DUMMY_MIGRATION") as String?
	)

	// Determines whether Flyway's clean operation is disabled.
	// This property is typically set via an environment variable (FLYWAY_CLEAN_DISABLED).
	// Disabling clean is recommended in production to prevent accidental data loss.
	cleanDisabled = (project.findProperty("FLYWAY_CLEAN_DISABLED") as String?)?.toBoolean() ?: false

	// Optional: If Flyway has trouble auto-detecting, you can set:
	driver = project.findProperty("DB_DRIVER") as String?
}

// ----------------------------------------------------------------------
// SonarQube Configuration
// ----------------------------------------------------------------------
sonar {
	properties {
		property("sonar.verbose", "true")
		property("sonar.log.level", "DEBUG")
		property("org.gradle.debug", "true")
		property("sonar.java.debug", "true")
		property("sonar.java.binaries", "build/classes")
		property("sonar.projectKey", "candido-server")
		property("sonar.projectName", "candido-server")
		property("sonar.host.url", project.findProperty("SONAR_URL") as String)
		property("sonar.token", project.findProperty("SONAR_TOKEN") as String)
	}
}

// ----------------------------------------------------------------------
// Test Configuration
// ----------------------------------------------------------------------
tasks.withType<Test> {
	useJUnitPlatform()
}

// ----------------------------------------------------------------------
// Jar Configuration
// ----------------------------------------------------------------------
tasks.withType<Jar> {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	layered {}
}
