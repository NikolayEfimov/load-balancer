package org.example.strategy;

import java.util.List;

public class RoundRobinBalancerStrategy implements LoadBalancerStrategy {

    private int currentIndex = -1;

    @Override
    public String get(List<String> servers) {
        if (servers.isEmpty()) throw new IllegalArgumentException("No servers registered");

        currentIndex = (currentIndex + 1) % servers.size();

        return servers.get(currentIndex);
    }
}
