subprojects {
    val jar: Jar by tasks
    jar.enabled = true

    dependencies {
        implementation(project(":search-common"))
        implementation(project(":search-application"))
        implementation(project(":search-domain"))
        api("org.springframework.cloud:spring-cloud-starter-openfeign")
    }
}