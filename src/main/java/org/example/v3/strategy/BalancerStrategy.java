package org.example.v3.strategy;

import java.util.List;

public interface BalancerStrategy {

    public String get(List<String> servers);
}
