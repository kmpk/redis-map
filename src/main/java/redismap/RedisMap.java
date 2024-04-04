package redismap;

import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * RedisMap implements the {@code Map<String, String>} interface using Redis as the underlying data store.
 * It provides key-value mapping functionality and operations commonly found in a Map interface.
 * This class is backed by a Redis client {@link Jedis}.
 */
public class RedisMap implements Map<String, String> {
    private final Jedis jedis;

    public RedisMap(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public int size() {
        return (int) jedis.dbSize();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key instanceof String) {
            return jedis.get((String) key) != null;
        }
        return false;
    }

    /**
     * Returns {@code true} if this RedisMap contains a mapping for the specified value.
     * This operation is not supported by RedisMap.
     *
     * @param value The value whose presence in this RedisMap is to be tested.
     * @return {@code true} if this RedisMap contains a mapping for the specified value, {@code false} otherwise.
     * @throws UnsupportedOperationException always, as RedisMap does not support this operation.
     */
    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get(Object key) {
        if (key instanceof String) {
            return jedis.get((String) key);
        }
        return null;
    }

    @Override
    public String put(String key, String value) {
        return jedis.setGet(key, value);
    }

    @Override
    public String remove(Object key) {
        if (key instanceof String) {
            return jedis.getDel((String) key);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        for (Map.Entry<? extends String, ? extends String> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        jedis.flushDB();
    }

    @Override
    public Set<String> keySet() {
        return jedis.keys("*");
    }

    /**
     * Returns a Set view of the mappings contained in this RedisMap.
     * This operation is not supported by RedisMap.
     *
     * @return A Set view of the mappings contained in this RedisMap.
     * @throws UnsupportedOperationException always, as RedisMap does not support this operation.
     */
    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a Set view of the mappings contained in this RedisMap.
     * This operation is not supported by RedisMap.
     *
     * @return A Set view of the mappings contained in this RedisMap.
     * @throws UnsupportedOperationException always, as RedisMap does not support this operation.
     */
    @Override
    public Set<Entry<String, String>> entrySet() {
        throw new UnsupportedOperationException();
    }
}
