package com.kyoulho.mid.auth.config

import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@Configuration
@EnableRedisHttpSession
class RedisConfig {
    // 추가적인 Redis 설정이 필요하다면 여기에 작성
}