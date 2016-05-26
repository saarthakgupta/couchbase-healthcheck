# Dropwizard Couchbase Healthcheck

This is a couchbase healthcheck which can be used to check the heath of individual couchbase nodes.
This library compiles only on Java 8.
 
## Dependencies
dropwizard-core: 0.9.2  
couchbase-client: 2.2.3

## Usage
Couchbase healthcheck makes it easy to monitor the health of individual nodes in the cluster by polling them and getting the status. It reports Healthy only if the status of all the nodes in the cluster is healthy.
 

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
```java
 final CouchbaseEnvironment couchbaseEnvironment = DefaultCouchbaseEnvironment.builder().build();
 private Cluster cluster = CouchbaseCluster.create(couchbaseEnvironment, getNodes()); // getNodes() method returns a List<String> of nodes.
 final CouchbaseClient couchbaseClient = new CouchbaseClient(getUserName(), getPassword(), cluster);// CouchbaseClient is bundled and needs username, password and cluster object to connect to hosts.
 environment.healthChecks().register("Couchbase", new CouchBaseHealthCheck(couchbaseClient));
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
