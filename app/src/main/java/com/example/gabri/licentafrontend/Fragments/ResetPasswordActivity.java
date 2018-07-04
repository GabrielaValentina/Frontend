package com.example.gabri.licentafrontend.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gabri.licentafrontend.R;

/**
 * Created by gabri on 3/26/2018.
 */

public class ResetPasswordActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.change_password_fragment, container, false);
    }
}
