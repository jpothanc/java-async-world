package org.java.async.world.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class OrderCache {
    ItemObserver<String> itemObserver;
    public OrderCache() {
        itemObserver = new ItemObserver<>();
    }
    public void start(){

        CompletableFuture.runAsync(() -> {
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    itemObserver.publish("Cache is running");
                    //System.out.println("\nCache is running" + " Thread:"+ Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ItemObserver<String> getObserver(){
       return itemObserver;
    }

}
