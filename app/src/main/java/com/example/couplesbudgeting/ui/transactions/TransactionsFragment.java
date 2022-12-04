package com.example.couplesbudgeting.ui.transactions;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.models.Transaction;
import com.example.couplesbudgeting.services.TransactionsService;
import com.example.couplesbudgeting.ui.dialogs.AddGoalBottomDialogFragment;
import com.example.couplesbudgeting.ui.dialogs.AddTransactionBottomDialogFragment;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//        userTransactions.add(new Transaction("Test1", "Cat1", 20.00, new Date()));
//        userTransactions.add(new Transaction("Test2", "Cat2", 20.00, new Date()));

        //Set up recycler view
        recyclerView = view.findViewById(R.id.transactionRecyclerView);
        TransactionsRecyclerViewAdapter adapter = new TransactionsRecyclerViewAdapter(userTransactions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
            System.out.println("Success!");
            userTransactions = transactions;
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

            public ViewHolder(View itemView) {
                super(itemView);
                transName = itemView.findViewById(R.id.transactionName);
                groupName = itemView.findViewById(R.id.transGroup);
                transAmount = itemView.findViewById(R.id.transAmount);
                balance = itemView.findViewById(R.id.balance);
            }
        }

        @NonNull
        @Override
        public TransactionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View transactionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);
            return new ViewHolder(transactionView);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionsRecyclerViewAdapter.ViewHolder holder, int position) {
            String transName = transactionList.get(position).getName();
            holder.transName.setText(transName);
            String groupName = transactionList.get(position).getCategory();
            holder.groupName.setText(groupName);
            Double transAmount = transactionList.get(position).getAmount();
            holder.transAmount.setText(transAmount.toString());
            //String balance = transactionList.get(position).get;
            holder.balance.setText("0.00");
        }

        @Override
        public int getItemCount() {
            return transactionList.size();
        }
    }
}