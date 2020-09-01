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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.atcampus.shopper.Fragment.SigninFragment.disableCloseBtn;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    public SignupFragment() {
        // Required empty public constructor
    }

    private TextView signinBtn;
    private FrameLayout parentFrameLayout;

    private EditText name, email, password, confirmPassword;
    private Button signUpBtn;
    private ImageButton closeBtn;
    private ProgressBar progressBar;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        signinBtn = view.findViewById(R.id.btnSignin);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrame);

        name = view.findViewById(R.id.editName);
        email = view.findViewById(R.id.editMail);
        password = view.findViewById(R.id.editPassword);
        confirmPassword = view.findViewById(R.id.editConfirmPassword);
        signUpBtn = view.findViewById(R.id.btnSignup);
        closeBtn = view.findViewById(R.id.closeBtn);
        progressBar = view.findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (disableCloseBtn) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUserData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUserData();
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
                checkUserData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUserData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                checkEmailPassword();
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
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    public void checkUserData() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(name.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())) {
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailPassword() {

        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Map<Object, String> userData = new HashMap<>();
                                    userData.put("name", name.getText().toString());
                                    userData.put("email", email.getText().toString());

                                    firebaseFirestore.collection("USERS").document(auth.getUid())
                                            .set(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Map<String, Object> listSize = new HashMap<>();
                                                        listSize.put("list_size", (long) 0);
                                                        firebaseFirestore.collection("USERS").document(auth.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                                                .set(listSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    mainIntent();
                                                                } else {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    signUpBtn.setEnabled(true);
                                                                    signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signUpBtn.setEnabled(true);
                                    signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                confirmPassword.setError("Password doesn't match!");
            }
        } else {
            email.setError("Invalid Email!");
        }
    }

    private void mainIntent() {
        if (disableCloseBtn) {
            disableCloseBtn = false;
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        getActivity().finish();
    }
}
