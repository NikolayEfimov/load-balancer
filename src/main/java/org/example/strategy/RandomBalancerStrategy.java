package org.example.strategy;

import java.util.List;
import java.util.Random;

public class RandomBalancerStrategy implements LoadBalancerStrategy {
    Random random = new Random();

    @Override
    public String get(List<String> servers) {
        var nextIndex = random.nextInt(servers.size());
        if (servers.isEmpty()) throw new IllegalArgumentException("No servers registered");

        return servers.get(nextIndex);
    }
}
