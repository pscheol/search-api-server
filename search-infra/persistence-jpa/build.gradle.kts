
apply {
    plugin("org.jetbrains.kotlin.plugin.jpa")
}

dependencies {
    implementation(project(":search-common"))
    implementation(project(":search-application"))
    implementation(project(":search-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

}