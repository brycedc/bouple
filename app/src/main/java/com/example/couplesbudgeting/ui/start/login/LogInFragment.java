package com.example.couplesbudgeting.ui.start.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.couplesbudgeting.MainActivity;
import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.models.User;
import com.example.couplesbudgeting.services.UsersService;

public class LogInFragment extends Fragment implements View.OnClickListener {

    private LogInViewModel mViewModel;

    private User logInUser;

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        //Initialize User
        logInUser = new User();

        //LogIn Button
        FrameLayout logInFrame = view.findViewById(R.id.loginButtonFrame);
        logInFrame.setOnClickListener(this);

        //EditTexts
        EditText logInEmailText = view.findViewById(R.id.logInEmailText);
        logInEmailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                logInUser.setEmailAddress(editable.toString());
            }
        });

        EditText logInPasswordText = view.findViewById(R.id.logInPasswordText);
        logInPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                logInUser.setPassword(editable.toString());
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButtonFrame:
                attemptLogIn();
                break;
        }
    }

    private void attemptLogIn() {
        UsersService usersService = new UsersService();
        boolean logInResult = usersService.LogInUser(logInUser);
        if (logInResult) {
            Toast.makeText(getActivity(), "Log In successful!", Toast.LENGTH_SHORT).show();
            launchMainActivity();
        }
        else {
            Toast.makeText(getActivity(), "Log In failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}