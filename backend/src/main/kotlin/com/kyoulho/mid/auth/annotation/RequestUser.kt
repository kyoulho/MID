package com.kyoulho.mid.auth.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

/**
 * 컨트롤러 메서드의 매개변수에 인증된 사용자의 ID를 주입하는 애노테이션.
 * fun someEndpoint(@RequestUserId userId: String) { ... }
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal(expression = "id")
annotation class RequestUserId

/**
 * 컨트롤러 메서드의 매개변수에 인증된 사용자의 이메일을 주입하는 애노테이션.
 * fun someEndpoint(@RequestUserEmail email: String) { ... }
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal(expression = "email")
annotation class RequestUserEmail
