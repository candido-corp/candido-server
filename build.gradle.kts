plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.sonarqube") version "5.0.0.4638"
}

group = "com.candido"
version = "1.1.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

val jsonwebtokenVersion = "0.12.6"
val springdocOpenApiVersion = "2.3.0"
val passayVersion = "1.6.4"
val hibernateJpaModelGeneratorVersion = "6.5.2.Final"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
	implementation("io.jsonwebtoken:jjwt-api:$jsonwebtokenVersion")
	// https://mvnrepository.com/artifact/org.passay/passay
	implementation("org.passay:passay:$passayVersion")
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenApiVersion")


	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	annotationProcessor("org.projectlombok:lombok")
	// https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-jpamodelgen
	annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:$hibernateJpaModelGeneratorVersion")


	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	compileOnly("org.projectlombok:lombok")


	// https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jsonwebtokenVersion")
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jsonwebtokenVersion")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

sonar {
	properties {
		property("sonar.verbose", "true")
		property("sonar.log.level", "DEBUG")
		property("org.gradle.debug", "true")
		property("sonar.java.debug", "true")
		property("sonar.java.binaries", "build/classes")
		property("sonar.projectKey", "candido-server")
		property("sonar.projectName", "candido-server")
		property("sonar.host.url", System.getenv("SONAR_URL"))
		property("sonar.token", System.getenv("SONAR_TOKEN"))
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}