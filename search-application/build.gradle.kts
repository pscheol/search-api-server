
val jar: Jar by tasks
jar.enabled = true

dependencies {
    implementation(project(":search-common"))
    implementation(project(":search-domain"))
    implementation("org.springframework:spring-tx:6.2.3")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
}