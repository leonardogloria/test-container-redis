package br.com.infinet.redis;

import br.com.infinet.redis.util.RedisBackedCache;
import redis.clients.jedis.Jedis;

public class CacheOpts {
    public static void main(String[] args) {
        Jedis localhost = new Jedis("localhost", 6379);
        localhost.set("Teste", "Teste");

    }
}
