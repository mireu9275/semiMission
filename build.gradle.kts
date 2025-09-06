plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
}

group = "kr.eme.semiMission"   // ✅ groupId
version = "1.0.0"              // ✅ 버전 고정 (SNAPSHOT 대신)

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("kr.eme.semiMoneyGlobal:SemiMoneyGlobal:1.0-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.jar {
    archiveFileName = "${project.name}-${project.version}.jar"
    destinationDirectory = file("C:\\Users\\Home\\Desktop\\Develop\\minecraft\\Bukkit\\paper 1.21.4 (Semicolon Primary Colony)\\plugins")
    manifest {
        attributes["Main-Class" ] = "kr.eme.semiMission.SemiMission"
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()      // ✅ group = "kr.eme.semiMission"
            artifactId = "SemiMission"              // ✅ artifactId
            version = project.version.toString()    // ✅ version = "1.0.0"
        }
    }
    repositories {
        mavenLocal() // ✅ publishToMavenLocal 실행 시 ~/.m2/repository 로 배포
    }
}
