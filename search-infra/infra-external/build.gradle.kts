subprojects {
    val jar: Jar by tasks
    jar.enabled = true

    dependencies {
        implementation(project(":search-common"))
        api("org.springframework.cloud:spring-cloud-starter-openfeign")
    }
}