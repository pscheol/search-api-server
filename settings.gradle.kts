plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "search-api-server"
include("search-api")
include("search-application")
include("search-common")
include("search-domain")

include("search-infra")
include("search-infra:infra-persistence")
findProject(":search-infra:infra-persistence")?.name = "infra-persistence"
include("search-infra:infra-external")
findProject(":search-infra:infra-external")?.name = "infra-external"
include("search-infra:infra-external:kakao-client")
findProject(":search-infra:infra-external:kakao-client")?.name = "kakao-client"
include("search-infra:infra-external:naver-client")
findProject(":search-infra:infra-external:naver-client")?.name = "naver-client"
