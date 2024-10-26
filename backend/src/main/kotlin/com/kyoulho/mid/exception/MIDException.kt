package com.kyoulho.mid.exception

import org.springframework.http.HttpStatus

class MIDException(
    val status: HttpStatus,
    override val message: String,
    override val cause: Throwable?
) : RuntimeException(message, cause) {
    constructor(status: HttpStatus, message: String) : this(status, message, null)
}
