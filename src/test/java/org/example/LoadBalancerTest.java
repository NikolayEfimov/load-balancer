package org.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
import org.example.exceptions.MaxServerNumberLimitExceed;
import org.example.exceptions.ServiceAlreadyRegistered;
import org.example.strategy.LoadBalancerStrategy;
import org.example.strategy.RandomBalancerStrategy;
import org.example.strategy.RoundRobinBalancerStrategy;
import org.junit.jupiter.api.Test;

class LoadBalancerTest {
    
    private LoadBalancer loadBalancer;

    private void initWithRandomStrategy() {
        initWithStrategy(new RandomBalancerStrategy());
    }

    private void initWithRoundRobinStrategy() {
        initWithStrategy(new RoundRobinBalancerStrategy());
    }

    private void initWithStrategy(LoadBalancerStrategy strategy) {
        loadBalancer = new LoadBalancer(strategy);
    }

    @Test
    public void testRegisterServerSuccess() {
        initWithRandomStrategy();
        loadBalancer.register("127.0.0.1");
        loadBalancer.register("192.168.1.1");
        
        assertThat(loadBalancer.getServers()).hasSize(2);
        assertThat(loadBalancer.getServers()).containsExactlyInAnyOrder("127.0.0.1", "192.168.1.1");
    }

    @Test
    public void testRegisterServerThrowsExceptionWhenServerAlreadyRegistered() {
        initWithRandomStrategy();
        loadBalancer.register("127.0.0.1");

        assertThatThrownBy(() -> loadBalancer.register("127.0.0.1"))
            .isInstanceOf(ServiceAlreadyRegistered.class);
    }

    @Test
    public void testRegisterServerThrowsExceptionWhenExceedMaxNumberOfServers() {
        initWithRandomStrategy();
        for (int i = 0; i < 10; i++) {
            loadBalancer.register("127.0.0." + i);
        }

        assertThatThrownBy(() -> loadBalancer.register("127.0.0.11"))
            .isInstanceOf(MaxServerNumberLimitExceed.class);
    }

    @Test
    public void testGetRandomServerSuccess() {
        initWithRandomStrategy();

        loadBalancer.register("127.0.0.1");
        loadBalancer.register("127.0.0.2");
        loadBalancer.register("127.0.0.3");

        var selectedServers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            selectedServers.add(loadBalancer.get());
        }

        assertThat(selectedServers).hasSizeGreaterThan(1);
    }

    @Test
    public void testGetRoundRobinStrategySuccess() {
        initWithRoundRobinStrategy();

        loadBalancer.register("127.0.0.1");
        loadBalancer.register("127.0.0.2");
        loadBalancer.register("127.0.0.3");

        var selectedServers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            selectedServers.add(loadBalancer.get());
        }

        assertThat(selectedServers).hasSizeGreaterThan(1);
    }

}
