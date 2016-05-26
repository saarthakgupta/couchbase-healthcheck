package com.saarthak.healthcheck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.saarthak.client.CouchbaseClient;

/**
 * @author saarthak.gupta
 *
 */
public class CouchBaseHealthCheckTest {

    private static CouchBaseHealthCheck couchBaseHealthCheck;
    private static CouchbaseClient couchbaseClient;

    @BeforeClass
    public static void setup() {
        couchbaseClient = Mockito.mock(CouchbaseClient.class);
        couchBaseHealthCheck = new CouchBaseHealthCheck(couchbaseClient);
    }

    @Test
    public void checkHealthy() throws Exception {
        when(couchbaseClient.getNodes()).thenReturn(getHealthyNodes());

        assertTrue(couchBaseHealthCheck.check().isHealthy());

    }

    @Test
    public void checkUnHealthy() throws Exception {
        when(couchbaseClient.getNodes()).thenReturn(getUnHealthyNodes());
        assertFalse(couchBaseHealthCheck.check().isHealthy());

    }

    private JsonArray getUnHealthyNodes() {
        JsonObject node = JsonObject.create();
        node.put("status", "unhealthy");
        node.put("hostname", "localhost");
        JsonObject node1 = JsonObject.create();
        node1.put("status", "healthy");
        node1.put("hostname", "localhost");
        JsonArray array = JsonArray.create();
        array.add(node);
        return array;
    }

    private JsonArray getHealthyNodes() {
        JsonObject node = JsonObject.create();
        node.put("status", "healthy");
        node.put("hostname", "localhost");
        JsonArray array = JsonArray.create();
        array.add(node);
        return array;
    }

}
