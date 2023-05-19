import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
}

group = "de.codecentric"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
    mavenLocal()
}

dependencies {
	implementation("de.codecentric:spring-boot-admin-starter-server:3.0.4")
	implementation("de.codecentric:spring-boot-admin-starter-client:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.86.Final:osx-aarch_64")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot {
    buildInfo()
}
