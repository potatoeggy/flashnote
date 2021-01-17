package com.example.flashnote.AI;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SpellChecker {
	protected static String host = "https://api.bing.microsoft.com/v7.0/";
	protected static String path = "spellcheck";
	
	protected static String key = "73506c522ea9455c910eb0511a82da17";
	
	protected static String mkt = "global";
	protected static String mode = "proof";
	
	protected static String check(String text) throws Exception {
		String params = "?mkt=" + mkt + "&mode=" + mode;
		URL url = new URL(host + path + params);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", key);
		connection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes("text=" + text);
		wr.flush();
		wr.close();
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		
		String line;
		String[] corrected = text.split(" ");
		String finalString = "";
		
		if ((line = in.readLine()) != null) {
			JsonArray result = JsonParser.parseString(line)
					                   .getAsJsonObject()
					                   .getAsJsonArray("flaggedTokens");
			int tokenIn = 0;
			String tokenName;
			
			for (int i = 0; i < corrected.length && tokenIn < result.size(); i++) {
				tokenName = result.get(tokenIn).getAsJsonObject().get("token").toString();
				tokenName = tokenName.substring(1, tokenName.length() - 1);
				
				if (corrected[i].equals(tokenName)) {
					corrected[i] = result.get(tokenIn).getAsJsonObject()
							               .get("suggestions").getAsJsonArray()
							               .get(0).getAsJsonObject()
							               .get("suggestion").getAsString();
					tokenIn++;
				}
			}
			
			for (String string : corrected) finalString += string + " ";
			
			return finalString;
		}
		in.close();
		return text;
	}
}
