package com.atcampus.shopper.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atcampus.shopper.Activity.MainActivity;
import com.atcampus.shopper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment {

    public SigninFragment() {
        // Required empty public constructor
    }

    private TextView registerBtn;
    private FrameLayout parentFrameLayout;

    private EditText email,password;
    private Button signinBtn;
    private ImageButton closeBtn;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        registerBtn = view.findViewById(R.id.registerBtn);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrame);

        email = view.findViewById(R.id.userMail);
        password = view.findViewById(R.id.userPassword);
        signinBtn = view.findViewById(R.id.btnSignin);
        closeBtn = view.findViewById(R.id.closeBtn);
        progressBar = view.findViewById(R.id.progressBar2);

        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignupFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserData();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right,R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputData() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                signinBtn.setEnabled(true);
                signinBtn.setTextColor(Color.rgb(255,255,255));
            }else {
                signinBtn.setEnabled(false);
                signinBtn.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signinBtn.setEnabled(false);
            signinBtn.setTextColor(Color.argb(50,255,255,255));
        }
    }

    private void checkUserData() {
        if (email.getText().toString().matches(emailPattern)){
            if (password.length() >= 8){

                progressBar.setVisibility(View.VISIBLE);
                signinBtn.setEnabled(false);
                signinBtn.setTextColor(Color.argb(50,255,255,255));

                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    mainIntent();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signinBtn.setEnabled(true);
                                    signinBtn.setTextColor(Color.rgb(255,255,255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }else{
                Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_LONG).show();
        }
    }

    private  void mainIntent(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
