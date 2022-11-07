package com.example.couplesbudgeting.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.couplesbudgeting.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddGoalBottomDialogFragment extends BottomSheetDialogFragment {
    public static AddGoalBottomDialogFragment newInstance() {
        return new AddGoalBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_goal, container,
                false);

        // get the views and attach the listener

        return view;

    }
}
