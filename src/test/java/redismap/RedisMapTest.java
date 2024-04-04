package redismap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RedisMapTest {
    private static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:7.2.4-alpine")).withExposedPorts(6379);
    private Jedis jedis = new Jedis(new HostAndPort(redis.getHost(), redis.getMappedPort(6379)));
    private RedisMap map = new RedisMap(jedis);

    @BeforeAll
    static void setup() {
        redis.start();
    }

    @AfterAll
    static void tearDown() {
        redis.stop();
    }

    @BeforeEach
    void init() {
        jedis.flushDB();
    }

    @Test
    void size() {
        assertEquals(0, map.size());
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4", "1");
        map.put("5", "1");
        assertEquals(5, map.size());
    }

    @Test
    void isEmpty() {
        assertTrue(map.isEmpty());
        map.put("1", "1");
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    void containsKey() {
        assertFalse(map.containsKey("key"));
        map.put("key", "1");
        assertTrue(map.containsKey("key"));
    }

    @Test
    void containsValue() {
        assertThrows(UnsupportedOperationException.class, () -> map.containsValue("val"));
    }

    @Test
    void getPut() {
        assertNull(map.get("key"));
        map.put("key", "1");
        assertEquals("1", map.get("key"));
    }

    @Test
    void remove() {
        assertNull(map.get("key"));
        map.put("key", "1");
        assertEquals("1", map.get("key"));
        map.remove("key");
        assertNull(map.get("key"));
    }

    @Test
    void putAll() {
        Map<String, String> newMap = Map.of("1", "1", "2", "2", "3", "3");
        map.putAll(newMap);
        assertEquals("1", map.get("1"));
        assertEquals("2", map.get("2"));
        assertEquals("3", map.get("3"));
    }

    @Test
    void keySet() {
        Map<String, String> newMap = Map.of("1", "1", "2", "2", "3", "3");
        map.putAll(newMap);
        assertEquals(Set.of("1", "2", "3"), map.keySet());
    }

    @Test
    void values() {
        assertThrows(UnsupportedOperationException.class, () -> map.values());
    }

    @Test
    void entrySet() {
        assertThrows(UnsupportedOperationException.class, () -> map.entrySet());
    }
}