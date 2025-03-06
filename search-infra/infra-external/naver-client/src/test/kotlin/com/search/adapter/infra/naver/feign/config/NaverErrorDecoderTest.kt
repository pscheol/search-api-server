package com.search.adapter.infra.naver.feign.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.search.adapter.infra.naver.feign.dto.NaverErrorResponse
import com.search.common.exception.ErrorType
import com.search.common.exception.SearchAPIException
import feign.Request
import feign.Response
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import java.io.ByteArrayInputStream

class NaverErrorDecoderTest : BehaviorSpec({
    val objectMapper = mockk<ObjectMapper>()
    val errorDecoder = NaverErrorDecoder(objectMapper)

    given("NaverErrorDecoder에서 오류 발생 시 SearchAPIException 예외 발생 테스트") {
        val responseBody: Response.Body = mockk<Response.Body>()
        val inputStream = ByteArrayInputStream(byteArrayOf(0))

        val response: Response = Response.builder()
            .status(400)
            .request(Request.create(Request.HttpMethod.GET, "testUrl", emptyMap(), Request.Body.empty(), null))
            .body(responseBody)
            .build()

        val extractNaverErrorResponse = NaverErrorResponse("SE03", "Invalid start value (부적절한 start 값입니다.)")


        every { responseBody.asInputStream() } returns inputStream
        every { objectMapper.readValue(any<String>(), NaverErrorResponse::class.java) } returns extractNaverErrorResponse


        When("decode를 호출하면") {
            val exception = shouldThrow<SearchAPIException> {
                errorDecoder.decode("testMethod", response)
            }
            then("SearchAPIException이 throw 된다.") {
                exception.message?.shouldBeEqual("Invalid start value (부적절한 start 값입니다.)")
                exception.httpStatus shouldBe HttpStatus.BAD_REQUEST
                exception.errorType shouldBe ErrorType.EXTERNAL_API_ERROR
            }
        }
    }
})