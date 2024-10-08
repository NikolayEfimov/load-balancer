package org.example.v3.strategy;

import java.util.List;
import java.util.Random;

public class RandomBalancerStrategy implements BalancerStrategy {

    private Random random = new Random();

    @Override
    public String get(List<String> servers) {
        var nextIndex = random.nextInt(servers.size());

        return servers.get(nextIndex);
    }
}
