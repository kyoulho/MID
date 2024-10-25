package com.kyoulho.mid.exception

import org.springframework.http.HttpStatus

class MIDException(
    val status: HttpStatus,
    override val message: String
) : RuntimeException(message)
