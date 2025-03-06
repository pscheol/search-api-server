package com.search.common.logger

import mu.KLogger
import mu.KotlinLogging

interface DefaultLogger {
    val log: KLogger
        get() = KotlinLogging.logger(this::class.qualifiedName ?: "UnknownClass")
}