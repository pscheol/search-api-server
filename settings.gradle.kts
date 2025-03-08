plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "search-api-server"
include("search-api")
include("search-application")
include("search-common")
include("search-domain")

include("search-infra")
include("search-infra:persistence-jpa")
findProject(":search-infra:infra-persistence-jpa")?.name = "infra-persistence-jpa"
include("search-infra:externals")
findProject(":search-infra:externals")?.name = "externals"
include("search-infra:externals:kakao-client")
findProject(":search-infra:externals:kakao-client")?.name = "kakao-client"
include("search-infra:externals:naver-client")
findProject(":search-infra:externals:naver-client")?.name = "naver-client"