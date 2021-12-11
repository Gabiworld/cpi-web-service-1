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

        // API CACHE (SESSION KEYS)
        // adamant-trail
        // https://railway.app/project/1d792c29-5414-47bb-afb3-19b3d56cec66
        connection.put(1, client.connect(RedisURI.builder()
                .withHost("containers-us-west-16.railway.app")
                .withPort(7450)
                .withPassword("iOcfyZjzHsX7TjSHmswJ")
                .withDatabase(0)
                .build()
        ));

        // GAME CACHE (JOIN ROOM DATA, QUEST DATA)
        // truthful-minute
        // https://railway.app/project/00ba2ba4-3e7b-4d66-80ee-0486115257fa
        connection.put(2, client.connect(RedisURI.builder()
                .withHost("containers-us-west-16.railway.app")
                .withPort(5546)
                .withPassword("I9cMc4X7oozQGncKWPBB")
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
