package com.example.flashnote.AI;

import com.example.flashnote.data.ApiService;
import com.example.flashnote.data.DataStateHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SpellChecker {
	// too lazy to find where it's called
	protected static String check(String text) {
		ApiService.spellCheck(text);
		return DataStateHelper.getCorrectedString();
	}
}
