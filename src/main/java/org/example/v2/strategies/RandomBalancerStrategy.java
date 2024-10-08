package org.example.v2.strategies;

import java.util.List;
import java.util.Random;

public class RandomBalancerStrategy implements BalancerStrategy {

    Random rand = new Random();

    @Override
    public String getServer(List<String> servers) {
        var nextIndex = rand.nextInt(servers.size());

        return servers.get(nextIndex);
    }
}
