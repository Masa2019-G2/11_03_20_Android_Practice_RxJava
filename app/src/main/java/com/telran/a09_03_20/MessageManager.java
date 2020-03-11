package com.telran.a09_03_20;

public interface MessageManager {
    void subscribe(Subscriber subscriber);
    void unsubscribe(Subscriber subscriber);
    void push(String msg);
}
