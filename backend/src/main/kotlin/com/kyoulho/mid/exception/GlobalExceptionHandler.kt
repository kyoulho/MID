package com.kyoulho.mid.exception

import com.kyoulho.mid.logger.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {
    val log = logger()

    @ExceptionHandler(MIDException::class)
    fun handleMIDException(ex: MIDException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = System.currentTimeMillis(),
            status = ex.status.value(),
            error = ex.status.reasonPhrase,
            message = ex.message,
            path = extractPathFromWebRequest(request)
        )
        log.error(ex.message, ex)
        return ResponseEntity(errorResponse, ex.status)
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAuthorizationDeniedException(
        ex: AuthorizationDeniedException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = System.currentTimeMillis(),
            status = 403,
            error = ex.localizedMessage,
            message = ex.localizedMessage,
            path = extractPathFromWebRequest(request)
        )
        log.error(ex.message, ex)
        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = System.currentTimeMillis(),
            status = 501,
            error = ex.toString(),
            message = ex.message ?: "Internal Server Error",
            path = extractPathFromWebRequest(request)
        )
        log.error(ex.message, ex)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // 기타 예외 핸들러도 여기에 추가 가능

    private fun extractPathFromWebRequest(request: WebRequest): String {
        val description = request.getDescription(false)
        return description.substringAfter("uri=", "")
    }
}

data class ErrorResponse(
    val timestamp: Long,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
