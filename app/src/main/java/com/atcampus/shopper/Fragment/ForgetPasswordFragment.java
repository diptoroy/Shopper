package com.atcampus.shopper.Fragment;

import android.graphics.Color;
import android.icu.util.ValueIterator;
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
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atcampus.shopper.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragment extends Fragment {

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    private FrameLayout parentFrameLayout;

    private EditText userEmail;
    private TextView checkText;
    private ImageButton backBtn;
    private Button resetBtn;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrame);
        userEmail = view.findViewById(R.id.userMail);
        checkText = view.findViewById(R.id.textView3);
        backBtn = view.findViewById(R.id.backBtn);
        resetBtn = view.findViewById(R.id.resetBtn);
        progressBar = view.findViewById(R.id.progressBar3);

        auth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUserInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right,R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkUserInput() {

        if (TextUtils.isEmpty(userEmail.getText())){
            resetBtn.setEnabled(false);
            resetBtn.setTextColor(Color.argb(50,255,255,255));
        }else {
            resetBtn.setEnabled(true);
            resetBtn.setTextColor(Color.rgb(255,255,255));
        }
    }
}
