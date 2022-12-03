package com.example.couplesbudgeting.ui.reports;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.models.Transaction;
import com.example.couplesbudgeting.services.TransactionsService;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportsFragment extends Fragment {

    private ReportsViewModel mViewModel;
    TextView total;
    TextView income;
    TextView expenditures;
    TextView savings;

    View view;

    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        // TODO: Use the ViewModel

        total = view.findViewById(R.id.reports_total_value);
        income = view.findViewById(R.id.reports_income_value);
        expenditures = view.findViewById(R.id.reports_expenditures_value);
        savings = view.findViewById(R.id.reports_savings_value);


        long millis = System.currentTimeMillis();
        Date todayDate = new Date(millis);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date monthAgo = cal.getTime();

        TransactionsService transactionsService = new TransactionsService();
        transactionsService.getTransactionInTimeframe(monthAgo, todayDate, new ReportsTransactions());

    }

    public class ReportsTransactions implements TransactionsService.ITransactionsReturn {

        @Override
        public void onSuccess(List<Transaction> transactions) {
            System.out.println("Success!");
            System.out.println(transactions.toString());

            double totalValue = 0.0;
            double totalIncome = 0.0;
            double totalExpense = 0.0;
            double totalSavings = 0.0;

            for (Transaction transaction : transactions) {
                double amount = transaction.getAmount();

                if (transaction.getCategory().equals("Income")) {
                    totalIncome += amount;
                }
                else if (transaction.getCategory().equals("Expense")) {
                    amount = amount * -1;
                    totalExpense += amount;
                }
                else if (transaction.getCategory().equals("Saving")) {
                    totalSavings += amount;
                }

                totalValue += amount;
            }

            String incomeString = "$" + String.format("%,.2f", totalIncome);
            income.setText(incomeString);

            String expenseString = "$" + String.format("%,.2f", totalExpense);
            expenditures.setText(expenseString);

            String savingsString = "$" + String.format("%,.2f", totalSavings);
            savings.setText(savingsString);

            String totalString = "$" + String.format("%,.2f", totalValue);
            total.setText(totalString);

        }
    }

}