package org.example.v3;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.example.v3.exceptions.MaxServerNumberExceedException;
import org.example.v3.exceptions.ServerAlreadyExistsException;
import org.example.v3.strategy.BalancerStrategy;

public class LoadBalancer {

    public static final int MAX_SERVER_COUNT = 10;
    private final List<String> servers = new ArrayList<>();
    private final BalancerStrategy strategy;
    private final ReadWriteLock lock;

    public LoadBalancer(BalancerStrategy strategy) {
        this.strategy = strategy;
        lock = new ReentrantReadWriteLock();
    }

    public void addServer(String address) {
        lock.writeLock().lock();
        try {
            if (servers.size() >= MAX_SERVER_COUNT) {
                throw new MaxServerNumberExceedException();
            }
            if (servers.contains(address)) {
                throw new ServerAlreadyExistsException();
            }
            servers.add(address);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<String> getServers() {
        return servers;
    }

    public String get() {
        return strategy.get(servers);
    }
}
