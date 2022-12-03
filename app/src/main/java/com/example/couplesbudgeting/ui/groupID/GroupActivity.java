package com.example.couplesbudgeting.ui.groupID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.couplesbudgeting.MainActivity;
import com.example.couplesbudgeting.R;
import com.example.couplesbudgeting.services.GroupsService;
import com.example.couplesbudgeting.ui.start.startPage.StartFragment;

public class GroupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText groupId;
    private Button joinGroup;
    private Button createGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_groupid);

        // Wires up widgets
        groupId = findViewById(R.id.provide_group_id);
        joinGroup = findViewById(R.id.join_group);
        createGroup = findViewById(R.id.create_group);

        joinGroup.setOnClickListener(this);
        createGroup.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.join_group:
                if(groupId.getText().toString().length() > 0) {
                    new GroupsService().AddUserToGroup(groupId.getText().toString(), getBaseContext(), this);
                }
                else {
                    Toast.makeText(getBaseContext(), "Please add a group ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.create_group:
                if(groupId.getText().toString().length() > 0) {
                    new GroupsService().createGroup(groupId.getText().toString(), getBaseContext(), this);
                }
                else {
                    Toast.makeText(getBaseContext(), "Please add a group ID", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
