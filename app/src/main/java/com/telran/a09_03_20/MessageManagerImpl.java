package com.telran.a09_03_20;

import java.util.HashSet;
import java.util.Set;

public class MessageManagerImpl implements MessageManager {
    Set<Subscriber> subscribers = new HashSet<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void push(String msg) {
        for(Subscriber s : subscribers){
            s.notify(msg);
        }
    }
}
