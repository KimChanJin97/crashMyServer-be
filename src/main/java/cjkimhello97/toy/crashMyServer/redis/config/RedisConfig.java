package cjkimhello97.toy.crashMyServer.redis.config;

import static org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP;

import cjkimhello97.toy.crashMyServer.redis.domain.AccessToken;
import cjkimhello97.toy.crashMyServer.redis.domain.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(
        basePackages = {"cjkimhello97.toy.crashMyServer.redis.repository"},
        enableKeyspaceEvents = ON_STARTUP)
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RefreshToken> refreshTokenRedisTemplate = new RedisTemplate<>();
        refreshTokenRedisTemplate.setConnectionFactory(connectionFactory);

        refreshTokenRedisTemplate.setKeySerializer(new StringRedisSerializer()); // memberId
        refreshTokenRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // RefreshToken
        refreshTokenRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        refreshTokenRedisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return refreshTokenRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, AccessToken> accessTokenRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, AccessToken> accessTokenRedisTemplate = new RedisTemplate<>();
        accessTokenRedisTemplate.setConnectionFactory(connectionFactory);

        accessTokenRedisTemplate.setKeySerializer(new StringRedisSerializer()); // memberId
        accessTokenRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // AccessToken
        accessTokenRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        accessTokenRedisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return accessTokenRedisTemplate;
    }
}
