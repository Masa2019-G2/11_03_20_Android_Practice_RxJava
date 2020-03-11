package com.telran.a09_03_20;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class Interactor {
    private PublishSubject<String> subject = PublishSubject.create();

    public void send(String str){
        subject.onNext(str);
    }

    public Disposable subscribe(Consumer<String> consumer){
        return subject.subscribe(consumer);
    }
}
