package com.example.couplesbudgeting.ui.dashboard;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.couplesbudgeting.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardViewModel mViewModel;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Sets up card navigation
        CardView nav_to_transaction = view.findViewById(R.id.transactions_card);
        CardView nav_to_goals = view.findViewById(R.id.goals_card);
        nav_to_transaction.setOnClickListener(this);
        nav_to_goals.setOnClickListener(this);

        // Sets up popup windows
        Button add_transaction = view.findViewById(R.id.add_trans_button);
        Button add_goal = view.findViewById(R.id.add_goal_button);
        add_transaction.setOnClickListener(this);
        add_goal.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        switch(view.getId()) {
            case R.id.transactions_card:
                navView.setSelectedItemId(R.id.navigation_transactions);
                break;
            case R.id.goals_card:
                navView.setSelectedItemId(R.id.navigation_goals);
                break;
            case R.id.add_trans_button:

                break;
            case R.id.add_goal_button:

                break;
        }
    }


}