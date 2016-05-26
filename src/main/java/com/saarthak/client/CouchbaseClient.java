package com.saarthak.client;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.saarthak.config.CouchbaseConfig;

import lombok.Getter;

@Getter
public class CouchbaseClient {

    private CouchbaseConfig config;
    private Cluster cluster;

    public CouchbaseClient(final CouchbaseConfig couchbaseConfig) {
        super();
        this.config = couchbaseConfig;
        final CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment.builder().build();
        this.cluster = CouchbaseCluster.create(couchbaseEnvironment, couchbaseConfig.getNodes());
    }

    public void shutdown() {
        this.cluster.disconnect();
        // this.cluster.disconnect(10, TimeUnit.SECONDS);
    }

    public JsonArray getNodes() {
        return (JsonArray) this.cluster.clusterManager(config.getUser(), config.getPassword()).info().raw().get("nodes");
    }
}
