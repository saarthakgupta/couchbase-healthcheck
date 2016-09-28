package com.saarthak.healthcheck;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.saarthak.client.CouchbaseClient;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * @author saarthak.gupta
 */
public class CouchBaseHealthCheckTest {

    private static CouchBaseHealthCheck defaultCouchBaseHealthCheck;
    private static CouchBaseHealthCheck couchBaseHealthCheck;
    private static CouchbaseClient couchbaseClient;
    private static int minimumHealthyNodes = 2;

    @BeforeClass
    public static void setup() {
        couchbaseClient = mock(CouchbaseClient.class);
        defaultCouchBaseHealthCheck = new CouchBaseHealthCheck(couchbaseClient);
        couchBaseHealthCheck = new CouchBaseHealthCheck(couchbaseClient, minimumHealthyNodes);
    }

    @Test
    public void checkThreeHealthy() throws Exception {
        reset(couchbaseClient);
        when(couchbaseClient.getNodes()).thenReturn(getThreeHealthyNodes());
        assertTrue(defaultCouchBaseHealthCheck.check().isHealthy());
    }

    @Test
    public void checkFiveHealthy() throws Exception {
        reset(couchbaseClient);
        when(couchbaseClient.getNodes()).thenReturn(getFiveHealthyNodes());
        assertTrue(defaultCouchBaseHealthCheck.check().isHealthy());

    }

    @Test
    public void checkThreeUnhealthy() throws Exception {
        reset(couchbaseClient);
        when(couchbaseClient.getNodes()).thenReturn(getThreeUnhealthyNodes());
        assertFalse(defaultCouchBaseHealthCheck.check().isHealthy());
    }


    @Test
    public void checkFiveUnhealthy() throws Exception {
        reset(couchbaseClient);
        when(couchbaseClient.getNodes()).thenReturn(getFiveUnhealthyNodes());
        assertFalse(defaultCouchBaseHealthCheck.check().isHealthy());
    }

    @Test
    public void checkThreeOutOfFiveHealthy() throws Exception {
        reset(couchbaseClient);
        when(couchbaseClient.getNodes()).thenReturn(getFiveUnhealthyNodes());
        assertTrue(couchBaseHealthCheck.check().isHealthy());
    }

    @Test
    public void checkTwoOutOfFiveHealthy() throws Exception {
        reset(couchbaseClient);
        when(couchbaseClient.getNodes()).thenReturn(getFiveUnhealthyNodes());
        assertTrue(couchBaseHealthCheck.check().isHealthy());
    }




    private JsonArray getThreeHealthyNodes() {
        final JsonArray array = JsonArray.create();
        array.add(createNode("healthy", "node1"));
        array.add(createNode("healthy", "node2"));
        array.add(createNode("unhealthy", "node3"));
        return array;
    }

    private JsonArray getThreeUnhealthyNodes() {
        final JsonArray array = JsonArray.create();
        array.add(createNode("unhealthy", "node1"));
        array.add(createNode("healthy", "node2"));
        array.add(createNode("unhealthy", "node3"));
        return array;
    }

    private JsonArray getFiveUnhealthyNodes() {
        final JsonArray array = JsonArray.create();
        array.add(createNode("healthy", "node1"));
        array.add(createNode("healthy", "node2"));
        array.add(createNode("unhealthy", "node3"));
        array.add(createNode("unhealthy", "node4"));
        array.add(createNode("unhealthy", "node5"));
        return array;
    }

    private JsonArray getFiveHealthyNodes() {
        final JsonArray array = JsonArray.create();
        array.add(createNode("healthy", "node1"));
        array.add(createNode("healthy", "node2"));
        array.add(createNode("unhealthy", "node3"));
        array.add(createNode("unhealthy", "node4"));
        array.add(createNode("healthy", "node5"));
        return array;
    }

    private JsonObject createNode(final String status, final String nodeName) {
        final JsonObject node1 = JsonObject.create();
        node1.put("status", status);
        node1.put("hostname", nodeName);
        return node1;
    }
}
