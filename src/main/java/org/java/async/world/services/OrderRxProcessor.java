package org.java.async.world.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.java.async.world.models.Order;

public class OrderRxProcessor {
    public static Observable<Order> dispatchOrder(String orderId) {
        OrderService orderService = new OrderService();

        return Observable.defer(() -> {
                    System.out.println("Fetching order: " + orderId + " on thread: " + Thread.currentThread().getName());
                    Order order = orderService.getOrder(orderId);
                    return Observable.just(order);
                })
                .subscribeOn(Schedulers.io()) // Fetch order on IO scheduler
                .observeOn(Schedulers.computation())
                .map(order -> {
                    System.out.println("Enriching order on thread: " + Thread.currentThread().getName());
                    return orderService.enrichOrder(order);
                })
                .map(order -> {
                    System.out.println("Performing payment on thread: " + Thread.currentThread().getName());
                    return orderService.performPayment(order);
                })
                .map(order -> {
                    System.out.println("Dispatching order on thread: " + Thread.currentThread().getName());
                    return orderService.dispatchOrder(order);
                })
                .doOnNext(order -> {
                    System.out.println("Sending email for order: " + order.getId() + " on thread: " + Thread.currentThread().getName());
                    orderService.sendEmail(order);
                })
                .doOnError(error -> {
                    System.out.println("Error occurred on thread: " + Thread.currentThread().getName() + ": " + error.getMessage());
                }).doOnComplete(() -> System.out.println("Completed emission"))
                .doFinally(() -> System.out.println("Observable completed"));
    }
}
