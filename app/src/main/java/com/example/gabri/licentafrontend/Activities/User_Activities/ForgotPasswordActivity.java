package com.example.gabri.licentafrontend.Activities.User_Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.gabri.licentafrontend.Fragments.ChangePasswordFragment;
import com.example.gabri.licentafrontend.R;

/**
 * Created by gabri on 3/26/2018.
 */

public class ForgotPasswordActivity  extends FragmentActivity{

    FragmentTransaction fragment;
    ChangePasswordFragment changePasswordFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_page);

        changePasswordFragment = new ChangePasswordFragment();

       /* FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(changePasswordFragment)
                .commit();
*/

    }
}
