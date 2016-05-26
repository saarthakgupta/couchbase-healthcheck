package com.saarthak.config;

import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * @author saarthak.gupta
 *
 */
@Data
@ToString(exclude = "password")
public class CouchbaseConfig {

    private List<String> nodes;
    private String user;
    private String password;

}
