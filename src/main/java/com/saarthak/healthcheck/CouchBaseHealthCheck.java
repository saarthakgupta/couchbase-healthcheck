package com.saarthak.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.saarthak.client.CouchbaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saarthak.gupta
 */
public class CouchBaseHealthCheck extends HealthCheck {
    private static final Logger log = LoggerFactory.getLogger(CouchBaseHealthCheck.class);
    private final CouchbaseClient couchbaseClient;
    private final Integer minimumHealthyNodes;

    public CouchBaseHealthCheck(final CouchbaseClient couchbaseClient) {
        this.couchbaseClient = couchbaseClient;
        this.minimumHealthyNodes = null;
    }

    public CouchBaseHealthCheck(final CouchbaseClient couchbaseClient, final int minimumHealthyNodes) {
        this.couchbaseClient = couchbaseClient;
        this.minimumHealthyNodes = minimumHealthyNodes;
    }


    @Override
    protected Result check() throws Exception {
        log.info("Running health check for couch base");
        final JsonArray nodes = couchbaseClient.getNodes();
        final List<String> healthyNodes = new ArrayList<>();
        final List<String> unhealthyNodes = new ArrayList<>();
        nodes.forEach(node -> {
            final JsonObject obj = (JsonObject) node;
            final boolean isNodeHealthy = "healthy".equals(obj.get("status"));
            if (isNodeHealthy) {
                healthyNodes.add(obj.getString("hostname"));
            } else {
                unhealthyNodes.add(obj.getString("hostname"));
            }
        });

        //isHealthy is derived to be true when 50% or more nodes are healthy.
        final boolean isHealthy = healthyNodes.size() >= (minimumHealthyNodes != null ? minimumHealthyNodes : unhealthyNodes.size());

        return isHealthy ? Result.healthy("Connected to Couchbase cluster. Healthy Nodes : " + healthyNodes)
                : Result.unhealthy("Not Connected to Couchbase cluster. Healthy Nodes: " + healthyNodes + ". Unhealthy Nodes: " + unhealthyNodes);
    }

}
