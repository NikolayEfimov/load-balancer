package org.example.v2.strategies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

class RandomBalancerStrategyTest {

    RandomBalancerStrategy strategy = new RandomBalancerStrategy();

    @Test
    void getServerRandomly() {
        var servers = List.of("127.0.0.1", "127.0.0.2", "127.0.0.3");

        var selectedServers = new HashSet<>();

        for(int i = 0; i < 3; i++) {
            selectedServers.add(strategy.getServer(servers));
        }

        assertThat(selectedServers).hasSizeGreaterThan(1);
    }
}
