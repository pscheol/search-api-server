val jar: Jar by tasks
jar.enabled = true

dependencies {
    implementation(project(":search-common"))
}