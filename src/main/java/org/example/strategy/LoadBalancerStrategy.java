package org.example.strategy;

import java.util.List;

public interface LoadBalancerStrategy {
    
    String get(List<String> servers);
}
