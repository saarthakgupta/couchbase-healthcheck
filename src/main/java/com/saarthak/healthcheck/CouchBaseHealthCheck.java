package com.saarthak.healthcheck;

import java.util.ArrayList;
import java.util.List;

import com.codahale.metrics.health.HealthCheck;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.saarthak.client.CouchbaseClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author saarthak.gupta
 *
 */
@AllArgsConstructor
@Slf4j
public class CouchBaseHealthCheck extends HealthCheck {
    private final CouchbaseClient couchbaseClient;

    @Override
    protected Result check() throws Exception {
        log.info("Running health check for couch base");

        JsonArray nodes = couchbaseClient.getNodes();
        Boolean isHealthy = true;
        final List<Boolean> nodesList = new ArrayList<>();
        List<String> healthyNodes = new ArrayList<>();
        List<String> unHealthyNodes = new ArrayList<>();
        nodes.iterator().forEachRemaining(node -> {
            JsonObject obj = (JsonObject) node;
            boolean isNodeHealthy = "healthy".equals(obj.get("status"));
            nodesList.add(isNodeHealthy);
            if (isNodeHealthy) {
                healthyNodes.add(obj.getString("hostname"));
            } else {
                unHealthyNodes.add(obj.getString("hostname"));
            }
        });

        for (Boolean node : nodesList) {
            isHealthy = isHealthy && node;
        }
        return isHealthy ? Result.healthy("Connected to Couchbase cluster. Healthy Nodes : " + healthyNodes)
                : Result.unhealthy("Not Connected to Couchbase cluster. Healthy Nodes: " + healthyNodes + ". Unhealthy Nodes: " + unHealthyNodes);
    }

}
