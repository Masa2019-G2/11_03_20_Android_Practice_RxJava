package com.telran.a09_03_20;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class Worker {

    Observable<String> doWork(){
        return Observable.create(emitter -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String json = "{\"name\":\"Vasya\",\"age\":23,\"address\":\"Tel Aviv\"}";
            emitter.onNext(json);
            emitter.onComplete();
        });
    }
}
