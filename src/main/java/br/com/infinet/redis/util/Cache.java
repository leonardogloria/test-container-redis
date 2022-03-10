package br.com.infinet.redis.util;

import java.util.Optional;

/**
 * Cache, for storing data associated with keys.
 */
public interface Cache {

    void put(String key, String value);


     Optional<String> get(String key);
}
