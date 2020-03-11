package com.telran.a09_03_20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MultiFragments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Interactor interactor = new Interactor();
        setContentView(R.layout.activity_multi_fragments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout2,FragmentB.newInstance(interactor))
                .replace(R.id.frameLayout,FragmentA.newInstance(interactor))
                .commit();
    }
}
