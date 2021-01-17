package com.example.flashnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flashnote.data.DataState;
import com.example.flashnote.data.DataStateHelper;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(State.justRegistered){
            State.justRegistered = false;
            Snackbar.make(findViewById(R.id.username), "Account registered successfully!", Snackbar.LENGTH_LONG).show();
        }

        if(State.justLoggedOut){
            State.justLoggedOut = false;
            Snackbar.make(findViewById(R.id.username), "Logged out successfully!", Snackbar.LENGTH_LONG).show();
        }

        final TextView username = findViewById(R.id.username);
        final TextView password = findViewById(R.id.password);
        Button auth = findViewById(R.id.loginButton);
        auth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String error = null;
                if(username.getText().toString().trim().length() == 0) error = "Username required";
                else if(password.getText().toString().trim().length() == 0) error = "Password required";
                else if(password.getText().toString().trim().length() < 8) error = "Password must be at least 8 characters long";
                if(error == null){
                    //TODO - DB request (still set error if bad)
                    if(error == null){
                        State.user = username.getText().toString().trim();
                        DataState.setLocalUsername(State.user);
                        State.justLoggedIn = true;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                if(error != null) Snackbar.make(v, error, Snackbar.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
