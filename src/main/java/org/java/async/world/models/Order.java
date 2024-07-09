package org.java.async.world.models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Order {
    private String id;
    private String status;

    public Order(String id, String status) {
        this.id = id;
        this.status = status;
    }
}

