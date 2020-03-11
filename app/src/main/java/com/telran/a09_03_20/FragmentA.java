package com.telran.a09_03_20;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentA extends Fragment {
    private Interactor interactor;

    public FragmentA() {
        // Required empty public constructor
    }

    public static FragmentA newInstance(Interactor interactor) {
        FragmentA fragment = new FragmentA();
        fragment.interactor = interactor;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        TextView resultTxt = view.findViewById(R.id.resultTxt);
        interactor.subscribe(str -> resultTxt.setText(str));
        return view;
    }
}
