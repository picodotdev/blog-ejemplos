package org.springframework.session.data.redis;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.MapSession;

import java.security.SecureRandom;
import java.time.Duration;

public class DefaultRedisOperationSessionRespository extends RedisOperationsSessionRepository {

    private static final String HEX_CHARACTERS = "0123456789ABCDEF";
    private static final int MAX_INACTIVE_INTERVAL_MINUTES = 30;
    private static final int SESSION_ID_LENGTH = 64;

    private SecureRandom randomGenerator;

    public DefaultRedisOperationSessionRespository(RedisOperations<Object, Object> sessionRedisOperations) {
        super(sessionRedisOperations);
    }

    @Override
    public RedisOperationsSessionRepository.RedisSession createSession() {
        if (randomGenerator == null) {
            randomGenerator = new SecureRandom();
        }

        byte[] bytes = new byte[SESSION_ID_LENGTH];
        randomGenerator.nextBytes(bytes);
        String id = getHex(bytes);

        RedisOperationsSessionRepository.RedisSession redisSession = new RedisOperationsSessionRepository.RedisSession(new MapSession(id));
        redisSession.setMaxInactiveInterval(Duration.ofMinutes(MAX_INACTIVE_INTERVAL_MINUTES));

        return redisSession;
    }

    private String getHex(byte [] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder hex = new StringBuilder( 2 * bytes.length);
        for (byte b : bytes) {
            hex.append(HEX_CHARACTERS.charAt((b & 0xF0) >> 4)).append(HEX_CHARACTERS.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}
