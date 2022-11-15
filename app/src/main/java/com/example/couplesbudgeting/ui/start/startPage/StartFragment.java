package com.example.couplesbudgeting.ui.start.startPage;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.ui.start.login.LogInFragment;
import com.example.couplesbudgeting.ui.start.register.RegisterFragment;

public class StartFragment extends Fragment implements View.OnClickListener {

    private StartViewModel mViewModel;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        //Buttons
        Button logInButton = view.findViewById(R.id.logInNavigateButton);
        logInButton.setOnClickListener(this);

        Button signUpButton = view.findViewById(R.id.signUpNavigateButton);
        signUpButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StartViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logInNavigateButton:
                navigateToLoginFragment();
                break;
            case R.id.signUpNavigateButton:
                navigateToRegisterFragment();
                break;
        }
    }

    private void navigateToLoginFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.startFragmentContainer, LogInFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToRegisterFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.startFragmentContainer, RegisterFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}