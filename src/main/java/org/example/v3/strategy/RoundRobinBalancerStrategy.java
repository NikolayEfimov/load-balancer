package org.example.v3.strategy;

import java.util.List;

public class RoundRobinBalancerStrategy implements BalancerStrategy {

    private int currentIndex = -1;

    @Override
    public String get(List<String> servers) {
        currentIndex = (currentIndex + 1) % servers.size();

        return servers.get(currentIndex);
    }
}
