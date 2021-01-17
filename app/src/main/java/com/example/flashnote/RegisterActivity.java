package com.example.flashnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView username = findViewById(R.id.usernameReg);
        TextView password = findViewById(R.id.passwordReg);
        Button auth = findViewById(R.id.registerButtonReg);
        auth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String error = null;
                if(username.getText().toString().trim().length() == 0) error = "Username required";
                else if(password.getText().toString().trim().length() == 0) error = "Password required";
                else if(password.getText().toString().trim().length() < 8) error = "Password must be atleast 8 characters long";
                if(error == null){
                    //TODO - DB request (still set error if bad)
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
