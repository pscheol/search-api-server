val bootJar by tasks
bootJar.enabled = true


dependencies {
    implementation(project(":search-common"))

    implementation(project(":search-application"))
    implementation(project(":search-domain"))
    implementation(project(":search-infra:persistence-jpa"))
    implementation(project(":search-infra:externals:naver-client"))
    implementation(project(":search-infra:externals:kakao-client"))

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.7.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

}