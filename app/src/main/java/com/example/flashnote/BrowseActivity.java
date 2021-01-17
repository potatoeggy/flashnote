package com.example.flashnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class BrowseActivity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();
    public void loadUsers(){
        users.add("rubber duck"); //would be data base stuffs
        users.add("another rubber duck"); //would be data base stuffs
        LinearLayout userLayout = findViewById(R.id.userLayout);
        for(final String user:users){
            Button curUser = new Button(this);
            curUser.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
            userLayout.addView(curUser);
            curUser.setText(user);
            curUser.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    State.viewingUser = user;
                    Intent intent = new Intent(BrowseActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        loadUsers();
    }
}
