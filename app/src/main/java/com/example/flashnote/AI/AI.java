package com.example.flashnote.AI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AI {
	//TODO: add card return
	
	/**
	 *
	 * @param imagePath relative path to JPEG image file
	 * @throws IOException if image file not found
	 * @return
	 */
	public static void parseImageToCard (String imagePath) throws IOException {
		ArrayList<String> sentences = NLP.parseSentences(Objects.requireNonNull(DetectText.detectText(imagePath)));
		for (String sentence : sentences) {
			NLP.analyzeText(sentence);
		}
	}
}
