# Couchbase Healthcheck

This is a Couchbase healthcheck module which can be used to check the health of Couchbase cluster.  
This library compiles only on Java 8.
 
## Dependencies
dropwizard-core: 1.0.2  
couchbase-client: 2.3.3

## Usage
Couchbase healthcheck makes it easy to monitor the health of individual nodes in the cluster by polling them and getting the status. It reports Healthy only if the status of more than half the nodes in the cluster is healthy.
 

### Maven Dependency

Use the following maven dependency:
```xml
<dependency>
  <groupId>org.clojars.saarthak</groupId>
  <artifactId>couchbase-healthcheck</artifactId>
  <version>1.0.2</version>
</dependency>
```

### Using Couchbase healthcheck with dropwizard
Default behavior: This will report healthy if atleast 50% of the nodes are healthy.
```java
 final CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment.builder().build();
 final Cluster cluster = CouchbaseCluster.create(couchbaseEnvironment, getNodes()); // getNodes() method returns a List<String> of nodes.
 final CouchbaseClient couchbaseClient = new CouchbaseClient(getUserName(), getPassword(), cluster);// CouchbaseClient is bundled and needs username, password and cluster object to connect to hosts.
 environment.healthChecks().register("Couchbase", new CouchBaseHealthCheck(couchbaseClient));
```

For Version 1.0.3 and above : This will report healthy if the number of healthy nodes is atleast equal to the minimum number of healthy nodes provided.
```java
 final CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment.builder().build();
 final Cluster cluster = CouchbaseCluster.create(couchbaseEnvironment, getNodes()); // getNodes() method returns a List<String> of nodes.
 final CouchbaseClient couchbaseClient = new CouchbaseClient(getUserName(), getPassword(), cluster);// CouchbaseClient is bundled and needs username, password and cluster object to connect to hosts.
 environment.healthChecks().register("Couchbase", new CouchBaseHealthCheck(couchbaseClient, minimumHealthyNodes));
```

LICENSE
-------

Copyright 2016 Saarthak Gupta <gupta_saarthak@yahoo.com>.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
