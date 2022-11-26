package com.example.couplesbudgeting.ui.goals;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.ui.dialogs.AddGoalBottomDialogFragment;
import com.example.couplesbudgeting.ui.dialogs.AddTransactionBottomDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GoalsFragment extends Fragment implements View.OnClickListener {

    private GoalsViewModel mViewModel;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        // Sets up popup windows
        Button add_goal = view.findViewById(R.id.add_goal_button_goals_fragment);
        add_goal.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GoalsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add_goal_button_goals_fragment:
                AddGoalBottomDialogFragment addGoalBottomDialogFragment =
                        AddGoalBottomDialogFragment.newInstance();
                addGoalBottomDialogFragment.show(
                        getParentFragmentManager(), "add goal");
                break;
        }
    }
}