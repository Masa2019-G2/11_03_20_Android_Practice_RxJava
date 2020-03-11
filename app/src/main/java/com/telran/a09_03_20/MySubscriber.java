package com.telran.a09_03_20;

import android.util.Log;

import java.util.Objects;
import java.util.UUID;

public class MySubscriber implements Subscriber {
    final String uuid = UUID.randomUUID().toString();

    @Override
    public void notify(String msg) {
        Log.d("MY_TAG", "notify: " + msg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MySubscriber that = (MySubscriber) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
