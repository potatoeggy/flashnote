package com.example.flashnote.AI;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.flashnote.data.Card;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AI {
	/**
	 *
	 * @param imagePath input stream of JPEG image file
	 * @return Arraylist of Cards
	 * @throws IOException if image file not found
	 * @see Card
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public static ArrayList<Card> parseImageToCard (InputStream imagePath) throws IOException {
		ArrayList<String> sentences = NLP.parseSentences(DetectText.detectText(imagePath));
		ArrayList<Card> cards = new ArrayList<>();
		for (String sentence : sentences) {
			cards.add(NLP.analyzeText(sentence));
		}
		return cards;
	}
}
