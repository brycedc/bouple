package com.example.couplesbudgeting.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.ui.transactions.TransactionsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

public class AddTransactionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private static TransactionsViewModel mViewModel;

    public static AddTransactionBottomDialogFragment newInstance() {
        return new AddTransactionBottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_transaction, container,
                false);

        Text trans_Name = view.findViewById(R.id.editTextTransName);
        Text category = view.findViewById(R.id.editTextCategory);
        Text amount = view.findViewById(R.id.editTextAmount);
        Text date = view.findViewById(R.id.editTextDate);
        Button add_transaction = view.findViewById(R.id.add_item_popup_button);
        add_transaction.setOnClickListener(this);

        // get the views and attach the listener

        return view;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add_item_popup_button:
                //TODO: Send information from popup to firebase, close popup.
                mViewModel.createTransaction();
                break;
        }
    }
}
