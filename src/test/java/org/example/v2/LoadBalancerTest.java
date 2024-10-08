package org.example.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.example.v2.exceptions.ServerLimitException;
import org.example.v2.exceptions.ServiceAlreadyRegisteredException;
import org.example.v2.strategies.RandomBalancerStrategy;
import org.junit.jupiter.api.Test;

class LoadBalancerTest {

    LoadBalancer loadBalancer = new LoadBalancer(new RandomBalancerStrategy());

    @Test
    public void addNewServerSuccessfully() {
        var server1 = "127.0.0.1";
        var server2 = "127.0.0.2";
        loadBalancer.addServer(server1);
        loadBalancer.addServer(server2);

        assertThat(loadBalancer.getServers()).hasSize(2);
        assertThat(loadBalancer.getServers()).containsExactlyInAnyOrder(server1, server2);
    }

    @Test
    public void throwExceptionWhenExceedMaxCapacity() {
        for (int i = 0; i < 10; i ++) {
            loadBalancer.addServer("127.0.0." + i);
        }

        assertThatThrownBy( () -> loadBalancer.addServer("127.0.0.11"))
            .isInstanceOf(ServerLimitException.class);
    }

    @Test
    public void throwExceptionWhenServiceAlreadyRegistered() {
        var server1 = "127.0.0.1";
        loadBalancer.addServer(server1);

        assertThatThrownBy( () -> loadBalancer.addServer(server1))
            .isInstanceOf(ServiceAlreadyRegisteredException.class);
    }

}
