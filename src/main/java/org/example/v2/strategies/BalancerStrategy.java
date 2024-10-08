package org.example.v2.strategies;

import java.util.List;

public interface BalancerStrategy {

    String getServer(List<String> servers);
}
