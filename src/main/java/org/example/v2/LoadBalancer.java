package org.example.v2;

import java.util.ArrayList;
import java.util.List;
import org.example.v2.exceptions.ServerLimitException;
import org.example.v2.exceptions.ServiceAlreadyRegisteredException;
import org.example.v2.strategies.BalancerStrategy;

public class LoadBalancer {

    private BalancerStrategy balancerStrategy;

    public static final int MAX_CAPACITY = 10;
    private final List<String> servers = new ArrayList<>(MAX_CAPACITY);

    public LoadBalancer(BalancerStrategy balancerStrategy) {
        this.balancerStrategy = balancerStrategy;
    }

    public void addServer(String address) {
        if (servers.contains(address)) {
            throw new ServiceAlreadyRegisteredException();
        }
        if (servers.size() >= MAX_CAPACITY) {
            throw new ServerLimitException();
        }
        servers.add(address);
    }

    public List<String> getServers() {
        return servers;
    }

    public String getServer() {
        return balancerStrategy.getServer(servers);
    }


}
