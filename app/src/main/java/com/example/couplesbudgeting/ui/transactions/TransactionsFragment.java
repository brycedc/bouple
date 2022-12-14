package com.example.couplesbudgeting.ui.transactions;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.models.Transaction;
import com.example.couplesbudgeting.services.TransactionsService;
import com.example.couplesbudgeting.ui.dialogs.AddGoalBottomDialogFragment;
import com.example.couplesbudgeting.ui.dialogs.AddTransactionBottomDialogFragment;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsFragment extends Fragment {

    private TransactionsViewModel mViewModel;
    private List<Transaction> userTransactions = new ArrayList<>();
    private RecyclerView recyclerView;

    public static TransactionsFragment newInstance() {
        return new TransactionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        mViewModel = new TransactionsViewModel();

        mViewModel.getAllUserTransactions(new TransactionsList());

        //Set up recycler view
        recyclerView = view.findViewById(R.id.transactionRecyclerView);


        // Sets up popup window
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

        ImageButton refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getAllUserTransactions(new TransactionsList());
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

    public class TransactionsList implements TransactionsService.ITransactionsReturn {
        @Override
        public void onSuccess(List<Transaction> transactions) {
            groupByDate(transactions);

            TransactionsRecyclerViewAdapter adapter = new TransactionsRecyclerViewAdapter(transactions);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }

        private void groupByDate(List<Transaction> transactions) {
            Date currentDate = new Date();
            for (Transaction transaction : transactions) {
                if (transaction.getDate().compareTo(currentDate) != 0) {
                    transaction.setFirstOnDate(true);
                    currentDate = transaction.getDate();
                }
                else {
                    transaction.setFirstOnDate(false);
                }
            }
        }
    }

    private class TransactionsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder> {

        private List<Transaction> transactionList = new ArrayList<>();

        public TransactionsRecyclerViewAdapter(List<Transaction> transactionList) {
            this.transactionList = transactionList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView transName;
            private TextView groupName;
            private TextView transAmount;
            private TextView balance;
            private TextView dateHeader;

            public ViewHolder(View itemView) {
                super(itemView);
                dateHeader = itemView.findViewById(R.id.dateHeader);
                transName = itemView.findViewById(R.id.transactionName);
                groupName = itemView.findViewById(R.id.transGroup);
                transAmount = itemView.findViewById(R.id.transAmount);
                balance = itemView.findViewById(R.id.balance);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (transactionList.get(position).isFirstOnDate()) {
                return 1;
            }
            return 0;
        }

        @NonNull
        @Override
        public TransactionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View transactionView = null;
            if (viewType == 1) {
                transactionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_with_date, parent, false);
            }
            else {
                transactionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);
            }
            return new ViewHolder(transactionView);
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void onBindViewHolder(@NonNull TransactionsRecyclerViewAdapter.ViewHolder holder, int position) {
            if (holder.dateHeader != null) {
                DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy", Locale.US);
                String date = dateFormat.format(transactionList.get(position).getDate());
                holder.dateHeader.setText(date);
            }
            String transName = transactionList.get(position).getName();
            holder.transName.setText(transName);
            String groupName = transactionList.get(position).getCategory();
            holder.groupName.setText(groupName);
            Double transAmount = transactionList.get(position).getAmount();
            String category = transactionList.get(position).getCategory();
            if (category.equals("Expense")) {
                holder.transAmount.setText("- " + String.format("%.2f", transAmount));
                holder.transAmount.setTextColor(getResources().getColor(R.color.Red));
            }
            else {
                holder.transAmount.setText("+ " + String.format("%.2f", transAmount));
                holder.transAmount.setTextColor(getResources().getColor(R.color.Green));
            }
            //String balance = transactionList.get(position).get;
            holder.balance.setText("0.00");
        }

        @Override
        public int getItemCount() {
            return transactionList.size();
        }
    }
}