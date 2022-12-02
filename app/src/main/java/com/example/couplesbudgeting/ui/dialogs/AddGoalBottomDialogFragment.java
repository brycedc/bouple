package com.example.couplesbudgeting.ui.dialogs;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.models.Goal;
import com.example.couplesbudgeting.services.GoalsService;
import com.example.couplesbudgeting.ui.watchers.MoneyTextWatcher;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGoalBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private Button add_goal;
    private EditText goal_name;
    private EditText amount;
    private EditText dateEdt;


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

        // Wires up widgets
        goal_name = view.findViewById(R.id.goal_name);
        amount = view.findViewById(R.id.goal_amount);
        add_goal = view.findViewById(R.id.add_item_popup_button);
        dateEdt = view.findViewById(R.id.goal_date);

        // Listens and changes amount to US currency
        amount.addTextChangedListener(new MoneyTextWatcher(amount));

        // OnClick Listeners
        add_goal.setOnClickListener(this);
        dateEdt.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add_item_popup_button:
                // Checks that all the fields are populated
                if(goal_name.getText().toString().length() > 0 &&
                        amount.getText().toString().length() > 0
                ) {
                    // Creates a date object from the date field
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = null;
                    try {
                        date = formatter.parse(dateEdt.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //Creates a new goal
                    String name = goal_name.getText().toString();
                    Double price = Double.parseDouble(amount.getText().toString().substring(1));
                    Goal newGoal = new Goal(name, price, 0, date);
                    new GoalsService().createGoal(newGoal);
                    this.dismiss();
                }
                else {
                    Toast.makeText(getContext(), "Please populate the required fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.goal_date:
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
