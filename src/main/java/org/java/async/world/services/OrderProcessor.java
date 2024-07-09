package org.java.async.world.services;

import org.java.async.world.models.Order;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.currentThread;

public class OrderProcessor {
    public static CompletableFuture<Order> dispatchOrder(String orderId) {
        System.out.println("Fetching orders asynchronously" + "on thread: " + currentThread().getName());
        ExecutorService cpuBound =  Executors.newFixedThreadPool(4);
        ExecutorService ioBound =  Executors.newCachedThreadPool();
        OrderService orderService = new OrderService();
        CompletableFuture<Order> future = CompletableFuture.supplyAsync(() -> {
                    System.out.println("Fetching order: " + orderId + " on thread: " + Thread.currentThread().getName());
                    return orderService.getOrder(orderId);
                },cpuBound)
                .thenApplyAsync(order -> {
                    System.out.println("Enriching order on thread: " + Thread.currentThread().getName());
                    return orderService.enrichOrder(order);
                },ioBound)
                .thenApply(order -> {
                    System.out.println("Performing payment on thread: " + Thread.currentThread().getName());
                    return orderService.performPayment(order);
                })
                .thenApplyAsync(order -> {
                    System.out.println("Dispatching order on thread: " + Thread.currentThread().getName());
                    return orderService.dispatchOrder(order);
                })
                .thenApply(order -> {
                    System.out.println("Sending email for order: " + order.getId() + " on thread: " + Thread.currentThread().getName());
                    orderService.sendEmail(order);
                    return order;
                })
                .exceptionally(e -> {
                    System.out.println("Error: " + e.getMessage() + " on thread: " + Thread.currentThread().getName());
                    return null;
                });

        return future;
    }

//    public void test(){
//        for(int id = 0; id < 100; id++)
//        {
//            ExecutorService ioCachedThreadPool =  Executors.newCachedThreadPool();
//            ExecutorService cpuFixedThreadPool =  Executors.newFixedThreadPool(4);
//            OrderService orderService = new OrderService();
//            CompletableFuture<Void> future =
//                    // cpuFixedThreadPool - runs on Cached Thread Pool
//                    CompletableFuture.supplyAsync(() -> orderService.getOrder("1234"), ioCachedThreadPool)
//                            //cpuFixedThreadPool - runs on Fixed Thread pool
//                            .thenApplyAsync(order->orderService.enrichOrder(order),cpuFixedThreadPool)
//                            //Since this is Apply, it inherits the thread pool from the above task
//                            //which is cpuFixedThreadPool - runs on Fixed Thread pool
//                            .thenApply(order->orderService.performPayment(order))
//                            // Since this is ApplyAsync it runs on ForkJoinCommonPool
//                            .thenApplyAsync(orderService::dispatchOrder)
//                            .thenAccept(orderService::sendEmail)
//                            .exceptionally(ex -> {
//                                System.err.println("An error occurred: " + ex.getMessage());
//                                return null;
//                            });
//        }
//        OrderService orderService = new OrderService();
//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(()->orderService.getOrder("1234"))
//                .thenApply(orderService::enrichOrder)
//                .thenApply(orderService::performPayment)
//                .exceptionally(ex -> new failedOrder())
//                .thenApply(orderService::dispatchOrder)
//                .thenAccept(orderService::sendEmail)
//                .exceptionally(ex -> {
//                    System.err.println("An error occurred: " + ex.getMessage());
//                    return null;
//                });
//    }
}
