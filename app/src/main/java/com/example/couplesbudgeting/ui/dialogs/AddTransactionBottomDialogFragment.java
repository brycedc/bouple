package com.example.couplesbudgeting.ui.dialogs;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.models.Transaction;
import com.example.couplesbudgeting.services.TransactionsService;
import com.example.couplesbudgeting.ui.watchers.MoneyTextWatcher;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTransactionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private final String[] categories = {"Income", "Expense"};
    private Button add_transaction;
    private EditText transaction_name;
    private EditText dateEdt;
    private EditText amount;
    private AutoCompleteTextView autoCompleteText;

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


        autoCompleteText = view.findViewById(R.id.auto_complete_transactions);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, categories);
        autoCompleteText.setAdapter(adapterItems);

        // Sets up text fields
        dateEdt = view.findViewById(R.id.date);
        transaction_name = view.findViewById(R.id.transaction_name);
        amount = view.findViewById(R.id.amount);

        // Adds textChangedListener to convert to us currency
        amount.addTextChangedListener(new MoneyTextWatcher(amount));

        // Sets up button
        add_transaction = view.findViewById(R.id.add_item_popup_button);

        // Sets the OnClickListener
        add_transaction.setOnClickListener(this);
        dateEdt.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add_item_popup_button:

                // Checks that all the fields are populated
                if(dateEdt.getText().toString().length() > 0 &&
                        transaction_name.getText().toString().length() > 0 &&
                        amount.getText().toString().length() > 0 &&
                        autoCompleteText.getText().toString().length() > 0
                ) {
                    // Creates a date object from the date field
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = null;
                    try {
                        date = formatter.parse(dateEdt.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Creates a new transaction
                    String name = transaction_name.getText().toString();
                    String category = autoCompleteText.getText().toString();

                    String moneyAmount = amount.getText().toString().substring(1);
                    String withoutCommas = moneyAmount.replace(",", "");

                    Double price = Double.parseDouble(withoutCommas);
                    Transaction newTransaction = new Transaction(name, category, price, date);
                    new TransactionsService().createTransaction(newTransaction);
                    this.dismiss();
                }
                else {
                    Toast.makeText(getContext(),"Please populate all fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.date:
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        (view1, year1, monthOfYear, dayOfMonth) -> {
                            // on below line we are setting date to our edit text.
                            dateEdt.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1);
                        }, year, month, day);
                datePickerDialog.show();
                break;
        }
    }
}
