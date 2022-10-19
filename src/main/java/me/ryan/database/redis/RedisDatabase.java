package me.legit.database.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import me.legit.APICore;
import me.legit.database.Database;

import java.util.HashMap;
import java.util.Map;

public class RedisDatabase implements Database {

    private RedisClient client;
    private Map<Integer, StatefulRedisConnection<String, String>> connection;

    @Override
    public void setup() {
        APICore.getLogger().info("Initializing Redis database...");

        client = RedisClient.create();

        connection = new HashMap<>();

        connection.put(1, client.connect(RedisURI.builder()
                .withHost("containers-us-west-69.railway.app")
                .withPort(5605)
                .withPassword("UMJS94FHEKuXj993qQZq")
                .withDatabase(0)
                .build()
        ));

        connection.put(2, client.connect(RedisURI.builder()
                .withHost("containers-us-west-100.railway.app")
                .withPort(7445)
                .withPassword("RI5zB02sVLkWcOCqhIDz")
                .withDatabase(1)
                .build()
        ));

        /* TODO RATE LIMIT
        connection.put(3, client.connect(RedisURI.builder().withDatabase(3).build())); */
    }

    @Override
    public void disable() {
        APICore.getLogger().info("Disabling Redis database...");

        for (StatefulRedisConnection<String, String> connections : connection.values()) {
            connections.close();
        }
        client.shutdown();
    }

    public RedisClient client() {
        return client;
    }

    public Map<Integer, StatefulRedisConnection<String, String>> connection() {
        return connection;
    }

}
