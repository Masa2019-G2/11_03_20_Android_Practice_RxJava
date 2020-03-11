package com.telran.a09_03_20;

import android.content.Context;
import android.widget.Toast;

import java.util.Objects;
import java.util.UUID;

public class SuperPuperSubscriber implements Subscriber {
    Context context;
    final String uuid = UUID.randomUUID().toString();

    public SuperPuperSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void notify(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperPuperSubscriber that = (SuperPuperSubscriber) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
