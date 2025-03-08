package com.search.adapter.infra.kakao.feign.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.search.adapter.infra.kakao.feign.dto.KakaoErrorResponse
import com.search.exception.ErrorType
import com.search.exception.SearchAPIException
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
import java.nio.charset.StandardCharsets

class KakaoErrorDecoderTest : BehaviorSpec({
    val objectMapper = mockk<ObjectMapper>()
    val errorDecoder = KakaoErrorDecoder(objectMapper)

    given("KakaoErrorDecoder에서 오류 발생 시 SearchAPIException 예외 발생 테스트") {
        val responseBody: Response.Body = mockk<Response.Body>()
        val inputStream = ByteArrayInputStream(byteArrayOf(0))

        val response: Response = Response.builder()
            .status(400)
            .request(Request.create(Request.HttpMethod.GET, "testUrl", emptyMap(), Request.Body.empty(), null))
            .body(responseBody)
            .build()

        val extractKakaoErrorResponse = KakaoErrorResponse("InvalidArgument", "size is more than max")
        val body = String(inputStream.readAllBytes(), StandardCharsets.UTF_8)

        every { responseBody.asInputStream() } returns inputStream
        every { objectMapper.readValue(any<String>(), KakaoErrorResponse::class.java) } returns extractKakaoErrorResponse


        When("decode를 호출하면") {
            val exception = shouldThrow<SearchAPIException> {
                errorDecoder.decode("testMethod", response)
            }
            then("SearchAPIException이 throw 된다.") {
                exception.message?.shouldBeEqual("size is more than max")
                exception.httpStatus shouldBe HttpStatus.BAD_REQUEST
                exception.errorType shouldBe ErrorType.EXTERNAL_API_ERROR
            }
        }
    }
})