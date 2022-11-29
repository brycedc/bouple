package com.example.couplesbudgeting.ui.dialogs;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.ui.transactions.TransactionsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionBottomDialogFragment extends BottomSheetDialogFragment {
    private static TransactionsViewModel mViewModel = new TransactionsViewModel();
    private EditText trans_Name;
    private EditText category;
    private EditText amount;
    private EditText date;

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

        trans_Name = view.findViewById(R.id.editTextTransName);
        category = view.findViewById(R.id.editTextCategory);
        amount = view.findViewById(R.id.editTextAmount);
        date = view.findViewById(R.id.editTextDate);
        Button add_transaction = view.findViewById(R.id.add_item_popup_button);
        add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable amountEditable = amount.getText();
                String amountStr = amountEditable.toString();
                Double amountDouble = Double.parseDouble(amountStr);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date dateDate = null;
                try {
                    dateDate = formatter.parse(date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mViewModel.createTransaction(trans_Name.getText().toString(), category.getText().toString(), amountDouble, dateDate);

                getParentFragmentManager().beginTransaction().remove(AddTransactionBottomDialogFragment.this).commit();
            }
        });

        return view;

    }
}











