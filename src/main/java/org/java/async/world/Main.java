package org.java.async.world;

import org.java.async.world.services.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        OrderProcessor.dispatchOrder("1234");
//        OrderRxProcessor.dispatchOrder("1234") .subscribe(
//                order -> System.out.println("Processing completed for order: " + order.getId() + " on thread: " + Thread.currentThread().getName()),
//                Throwable::printStackTrace
//        );;

        OrderCache orderCache = new OrderCache();
        orderCache.start();
        OrderCacheClient.run(orderCache.getObserver());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            var input = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}