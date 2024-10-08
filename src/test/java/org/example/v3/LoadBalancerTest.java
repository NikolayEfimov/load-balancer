package org.example.v3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.example.v3.exceptions.MaxServerNumberExceedException;
import org.example.v3.exceptions.ServerAlreadyExistsException;
import org.example.v3.strategy.BalancerStrategy;
import org.example.v3.strategy.RandomBalancerStrategy;
import org.junit.jupiter.api.Test;

class LoadBalancerTest {

    BalancerStrategy strategy = new RandomBalancerStrategy();
    LoadBalancer loadBalancer = new LoadBalancer(strategy);

    @Test
    void addServerSuccessfully() {
        var server1 = "127.0.0.1";

        loadBalancer.addServer(server1);

        assertThat(loadBalancer.getServers()).hasSize(1);
    }

    @Test
    void addServerThrowExceptionIfMaxServerCountExceed() {
        for (int i = 0; i < 10; i++) {
            loadBalancer.addServer("127.0.0." + i);
        }

        assertThatThrownBy(() ->
                               loadBalancer.addServer("127.0.0.11")
        ).isInstanceOf(MaxServerNumberExceedException.class);
    }

    @Test
    void addServerThrowExceptionIfServerAlreadyExists() {
        loadBalancer.addServer("127.0.0.1");

        assertThatThrownBy(() ->
                               loadBalancer.addServer("127.0.0.1")
        ).isInstanceOf(ServerAlreadyExistsException.class);
    }
}
