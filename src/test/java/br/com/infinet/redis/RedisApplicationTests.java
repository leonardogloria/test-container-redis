package br.com.infinet.redis;

import br.com.infinet.redis.model.Player;
import br.com.infinet.redis.util.Cache;
import br.com.infinet.redis.util.RedisBackedCache;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class RedisApplicationTests {
	private static GenericContainer genericContainer;
	private RedisBackedCache redisBackedCache;

	@BeforeAll
	static void init(){
		genericContainer = new GenericContainer(
				DockerImageName.parse("redis:6.2.6")
		).withExposedPorts(6379);
		genericContainer.start();
	}
	@BeforeEach
	void setUp(){
		Jedis jedis = new Jedis("localhost", genericContainer.getMappedPort(6379));
		this.redisBackedCache = new RedisBackedCache(jedis, "teste");
		jedis.flushAll();
	}
	@Test
	@DisplayName("Deve gravar no banco")
	void testCache(){
		Player luke = Player.builder().name("Luke").score(200L).build();
		this.redisBackedCache.insereCache(luke);
		//["Luke", 200]
		Optional<String> optionalPlayerScore = this.redisBackedCache.get(luke.getName());
		long score = Long.parseLong(optionalPlayerScore.get());
		assertEquals(200L, score);

	}
	@Test
	@DisplayName("Não deve gravar no banco")
	void testNaoGrava(){
		Player anakin = Player.builder().name("Anakin").score(2L).build();
		this.redisBackedCache.insereCache(anakin);
		//[Luke, 200]
		//Luke, 600
		//[Luke 600]
		Optional<String> optionalPlayerScore = this.redisBackedCache.get(anakin.getName());
		assertNull(optionalPlayerScore.orElse(null));
	}

	@AfterAll
	static void destroy(){
		genericContainer.stop();
	}


}

