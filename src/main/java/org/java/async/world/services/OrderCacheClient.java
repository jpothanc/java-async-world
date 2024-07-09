package org.java.async.world.services;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class OrderCacheClient {
    public static void run(ItemObserver<String> eventPublisher){

        Flowable<String> flowable = eventPublisher.subscribe().toFlowable(BackpressureStrategy.BUFFER);

        Disposable disposable = flowable
                .subscribeOn(Schedulers.io())
                .doOnNext(item -> System.out.println("Processing item: " + item))
                .observeOn(Schedulers.computation())
                .filter(data -> data.contains("Cache"))
                //.debounce(5, TimeUnit.SECONDS)
                .onBackpressureBuffer()
                .subscribe(data -> {
                    System.out.println("\nData received: " + data + " Thread:"+ Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(5);
                });


    }
}
