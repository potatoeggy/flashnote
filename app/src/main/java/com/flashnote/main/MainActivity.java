package com.flashnote.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.flashnote.R;
import com.flashnote.data.ApiService;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ApiService.main(new String[]{});
	}
}
