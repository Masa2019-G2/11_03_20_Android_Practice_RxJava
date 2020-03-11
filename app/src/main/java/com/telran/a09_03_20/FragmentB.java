package com.telran.a09_03_20;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentB extends Fragment {

    private Interactor interactor;

    public FragmentB() {
        // Required empty public constructor
    }

    public static FragmentB newInstance(Interactor interactor) {
        FragmentB fragment = new FragmentB();
        fragment.interactor = interactor;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_b, container, false);
        view.findViewById(R.id.clickBtn)
                .setOnClickListener(v -> {
                    interactor.send(UUID.randomUUID().toString());
                });
        return view;
    }
}
