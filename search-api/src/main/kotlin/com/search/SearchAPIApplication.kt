package com.search

import com.search.adapter.infra.kakao.feign.KakaoClient
import com.search.adapter.infra.naver.feign.NaverClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients


@EnableFeignClients(clients = [NaverClient::class, KakaoClient::class])
@SpringBootApplication
class SearchAPIApplication

fun main(args: Array<String>) {
    runApplication<SearchAPIApplication>(*args)
}