package com.atcampus.shopper.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.atcampus.shopper.Fragment.SigninFragment;
import com.atcampus.shopper.Fragment.SignupFragment;
import com.atcampus.shopper.R;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    public static boolean resetButtonBack = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        frameLayout = findViewById(R.id.registerFrame);
        if (setSignUpFragment){
            setSignUpFragment = false;
            setDefaultFragment(new SignupFragment());
        }else {
            setDefaultFragment(new SigninFragment());
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (resetButtonBack){
                resetButtonBack = false;
                setFragment(new SigninFragment());
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void setDefaultFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_left,R.anim.slide_out_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}
