package com.saarthak.client;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.document.json.JsonArray;

public class CouchbaseClient {

    private String user;
    private String password;
    private Cluster cluster;

    public CouchbaseClient(final String user, final String password, final Cluster cluster) {
        super();
        this.user = user;
        this.password = password;
        this.cluster = cluster;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void shutdown() {
        this.cluster.disconnect();
        // this.cluster.disconnect(10, TimeUnit.SECONDS);
    }

    public JsonArray getNodes() {
        return (JsonArray) this.cluster.clusterManager(this.getUser(), this.getPassword()).info().raw().get("nodes");
    }
}
