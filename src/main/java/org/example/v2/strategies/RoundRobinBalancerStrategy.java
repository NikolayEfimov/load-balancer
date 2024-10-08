package org.example.v2.strategies;

import java.util.List;

public class RoundRobinBalancerStrategy implements BalancerStrategy {

    private int currentIndex = -1;

    @Override
    public String getServer(List<String> servers) {
        currentIndex = 0;
        currentIndex = (currentIndex + 1) % servers.size();

        return servers.get(currentIndex);
    }
}
