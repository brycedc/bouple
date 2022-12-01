package com.example.couplesbudgeting.ui.transactions;

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

public class TransactionsFragment extends Fragment {

    private TransactionsViewModel mViewModel;

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        // Sets up popup windows
        Button add_trans = view.findViewById(R.id.add_trans_button_trans_fragment);
        add_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTransactionBottomDialogFragment addTransactionBottomDialogFragment =
                        AddTransactionBottomDialogFragment.newInstance();
                addTransactionBottomDialogFragment.show(
                        getParentFragmentManager(), "add transaction");

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        // TODO: Use the ViewModel
    }

}