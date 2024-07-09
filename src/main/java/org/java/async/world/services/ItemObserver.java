package org.java.async.world.services;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class ItemObserver<T> {
    PublishSubject<T> subject;
    public ItemObserver() {
        subject = PublishSubject.create();
    }

    public void publish(T event) {
        subject.onNext(event);
    }

    public Observable<T> subscribe() {
        return subject;
    }

}
