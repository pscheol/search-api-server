package com.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SearchAPIApplication

fun main(args: Array<String>) {
    runApplication<SearchAPIApplication>(*args)
}