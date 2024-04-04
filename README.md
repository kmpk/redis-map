RedisMap implements the Map<String, String> interface using Redis as the underlying data store. It provides key-value mapping functionality and operations commonly found in a Map interface. This class is backed by a Redis client library [Jedis](https://github.com/redis/jedis).

## Usage

Usage:
```java
Jedis jedis = new Jedis("localhost", 6379);
RedisMap redisMap = new RedisMap(jedis);

redisMap.put(...);
redisMap.get(...);
...
```
