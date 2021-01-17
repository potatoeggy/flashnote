package com.example.flashnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flashnote.data.ApiService;
import com.example.flashnote.data.DataStateHelper;
import com.example.flashnote.data.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView username = findViewById(R.id.usernameReg);
        final TextView password = findViewById(R.id.passwordReg);
        Button auth = findViewById(R.id.registerButtonReg);
        auth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String error = null;
                if(username.getText().toString().trim().length() == 0) error = "Username required";
                else if(password.getText().toString().trim().length() == 0) error = "Password required";
                else if(password.getText().toString().trim().length() < 8) error = "Password must be at least 8 characters long";
                if(error == null){
                    //TODO - DB request (still set error if bad)
                    ApiService.getUserByUsername(username.getText().toString().trim());
                    List<User> candidates = DataStateHelper.getClientUserList();
                    if (candidates == null) {
                        System.out.println("Network error");
                    } else if (candidates.size() == 0) {
                        ApiService.addUser(new User(username.getText().toString().trim(), password.getText().toString()));
                    } else {
                        error = "User already exists";
                    }
                    if(error == null){
                        State.justRegistered = true;
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
                if(error != null) Snackbar.make(v, error, Snackbar.LENGTH_LONG).show();
            }
        });
    }


}
