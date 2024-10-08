package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.example.exceptions.MaxServerNumberLimitExceed;
import org.example.exceptions.ServiceAlreadyRegistered;
import org.example.strategy.LoadBalancerStrategy;

public class LoadBalancer {

    private static final int MAX_INSTANCES = 10;
    private List<String> instances;

    private final ReadWriteLock lock;
    private final LoadBalancerStrategy loadBalancerStrategy;

    public LoadBalancer(LoadBalancerStrategy strategy) {
        this.instances = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
        this.loadBalancerStrategy = strategy;
    }

    public void register(String address) {
        lock.writeLock().lock();
        try {
            if (instances.contains(address)) {
                throw new ServiceAlreadyRegistered();
            }
            if (instances.size() >= MAX_INSTANCES) {
                throw new MaxServerNumberLimitExceed();
            }
            instances.add(address);
        }
        finally {
            lock.writeLock().unlock();
        }

    }

    public List<String> getServers() {
        return instances;
    }

    public String get() {
        return loadBalancerStrategy.get(instances);
    }

}
