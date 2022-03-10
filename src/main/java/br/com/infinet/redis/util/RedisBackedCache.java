package br.com.infinet.redis.util;


import br.com.infinet.redis.model.Player;
import redis.clients.jedis.Jedis;

import java.util.Optional;

public class RedisBackedCache implements Cache {
    private final Jedis jedis;
    private final String cacheName;

    public RedisBackedCache(Jedis jedis, String cacheName) {
        this.jedis = jedis;
        this.cacheName = cacheName;
    }

    @Override
    public void put(String key, String value) {
        this.jedis.set(key, value);
    }
    public void insereCache(Player player){
        if(player.getScore() > 100){
            this.put(player.getName(), player.getScore().toString());
        }
    }

    @Override
    public  Optional<String> get(String key) {
        String foundJson = this.jedis.get(key);


        if (foundJson == null) {
            return Optional.empty();
        }

        return Optional.of(foundJson);
    }
}
