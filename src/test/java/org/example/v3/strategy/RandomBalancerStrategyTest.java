package org.example.v3.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

class RandomBalancerStrategyTest {

    BalancerStrategy randomBalancerStrategy = new RandomBalancerStrategy();

    @Test
    void get() {
        var servers = List.of("127.0.0.1", "127.0.0.2", "127.0.0.3");

        var selectedServers = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            selectedServers.add(randomBalancerStrategy.get(servers));
        }

        assertThat(selectedServers).hasSizeGreaterThan(1);
    }
}
