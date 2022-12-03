package com.example.couplesbudgeting.ui.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.ui.start.login.LogInFragment;
import com.example.couplesbudgeting.ui.start.startPage.StartFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_start);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.startFragmentContainer, StartFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}