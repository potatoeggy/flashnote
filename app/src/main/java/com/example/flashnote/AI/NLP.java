package com.example.flashnote.AI;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.flashnote.data.Card;
import com.google.api.client.util.Lists;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class NLP {
	
	static final String jsonPath = "Flashnote-e9dcc3f03434.json";
	
	static public Context context;
	
	public NLP(Context context) {
		NLP.context = context;
	}
	
	protected static ArrayList<String> parseSentences (ArrayList<String> text) {
		ArrayList<String> parsed = new ArrayList<>();
		String continued = "";
		for (int i = 0; i < text.size(); i++) {
			String line = text.get(i);
			while (line.contains(".") || line.contains(";")) {
				parsed.add((continued + line.substring(0, line.indexOf('.') + 1))
						           .replace("=", "is")
						           .replace("->", "is"));
				continued = "";
				line = line.substring(line.indexOf('.')+1);
			}
			continued = line;
		}
		return parsed;
	}
	
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	protected static Card analyzeText (String text) throws IOException {
		AssetManager assetManager = context.getAssets();
		GoogleCredentials credentials = GoogleCredentials.fromStream(assetManager.open(jsonPath))
				.createScoped(Lists.newArrayList(Collections.singleton("https://www.googleapis.com/auth/cloud-platform")));
		LanguageServiceSettings languageServiceSettings =
				LanguageServiceSettings.newBuilder()
						.setCredentialsProvider(FixedCredentialsProvider.create(credentials))
						.build();
		
		try (LanguageServiceClient language = LanguageServiceClient.create(languageServiceSettings)) {
			//change text to text that needs to be analyzed
			Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
			AnalyzeSyntaxRequest request =
					AnalyzeSyntaxRequest.newBuilder()
							.setDocument(doc)
							.setEncodingType(EncodingType.UTF16)
							.build();
			// analyze the syntax in the given text
			AnalyzeSyntaxResponse response = language.analyzeSyntax(request);
			// Create adjacency array
			ArrayList<Token>[] adj = new ArrayList[response.getTokensList().size()];
			for (int i = 0 ; i < response.getTokensList().size(); i++) {
				adj[i] = new ArrayList<>();
			}
			Token root = null;
			for (Token token : response.getTokensList()) {
				if (token.getDependencyEdge().getLabel().toString().equals("ROOT")) {
				root = token; continue;
				}
				adj[token.getDependencyEdge().getHeadTokenIndex()].add(token);
				
			}
			//removes the period
			assert root != null;
			adj[root.getDependencyEdge().getHeadTokenIndex()].remove(adj[root.getDependencyEdge().getHeadTokenIndex()].size()-1);
			
			StringBuilder question = new StringBuilder(), answer = new StringBuilder();
			String interrogative = "What ", verb ="";
			
			//interrogative word - POBJ, ATTR, DOBJ
			int largest = -1; Token largestT = null;
			for (Token token : response.getTokensList()) {
				String label = token.getDependencyEdge().getLabel().toString();
				if (label.equals("POBJ") || label.equals("ATTR") || label.equals("DOBJ")) {
					if (largest < adj[response.getTokensList().indexOf(token)].size()) {
						largest = adj[response.getTokensList().indexOf(token)].size();
						largestT = token;
					}
					else if (largest == adj[response.getTokensList().indexOf(token)].size()) {
						if (distanceToRoot(largestT, root, adj, response, 0) >
								    distanceToRoot(token, root, adj, response, 0)) {
							largestT = token;
						}
					}
				}
			}
			
			if (largestT == null) return null;
 			Document document2 = Document.newBuilder().setContent(largestT.getText().getContent()).setType(Document.Type.PLAIN_TEXT).build();
			AnalyzeEntitiesRequest requestEntity =
					AnalyzeEntitiesRequest.newBuilder()
							.setDocument(document2)
							.setEncodingType(EncodingType.UTF16)
							.build();
			AnalyzeEntitiesResponse responseEntity = language.analyzeEntities(requestEntity);
			if (responseEntity.getEntitiesList().size() != 0) {
				String wordType = responseEntity.getEntitiesList().get(0).getType().toString();
				if (wordType.equals("LOCATION") || wordType.equals("ADDRESS")) interrogative = "Where ";
				else if (wordType.equals("DATE")) interrogative = "When ";
				else if (wordType.equals("PERSON") || wordType.equals("ORGANIZATION")) interrogative = "Who ";
			}
			
			//verb
			int skip = -1;
			for (Token token : adj[root.getDependencyEdge().getHeadTokenIndex()]) {
				if (token.getLemma().equals("be")) {
					interrogative += token.getText().getContent() + " ";
					skip = response.getTokensList().indexOf(token);
					verb = root.getText().getContent();
					break;
				}
			}
			
			if (skip == -1) {
				if (root.getLemma().equals("be")) {
					interrogative += "is ";
					skip = root.getDependencyEdge().getHeadTokenIndex();
				} else {
					if (root.getPartOfSpeech().getTense().toString().equals("PRESENT")) {
						interrogative += "does ";
					} else interrogative += "did ";
					verb = root.getLemma();
				}
			}
			
			//body
			Queue<Token> queue = new LinkedList<>();
			queue.add(largestT);
			int smallestIn = Integer.MAX_VALUE;
			while (!queue.isEmpty()) {
				Token curr = queue.poll();
				if (response.getTokensList().indexOf(curr) < smallestIn) {
					smallestIn = response.getTokensList().indexOf(curr);
				}
				queue.addAll(adj[response.getTokensList().indexOf(curr)]);
			}
			
			question.append(interrogative);
			for (int i = 0; i < smallestIn; i++) {
				if (i==skip) continue;
				if (i == root.getDependencyEdge().getHeadTokenIndex())
					question.append(verb).append(" ");
				else
					question.append(response.getTokensList().get(i).getText().getContent()).append(" ");
			}
			
			for (int i = smallestIn; i < response.getTokensList().size()-1; i++) {
				answer.append(response.getTokensList().get(i).getText().getContent()).append(" ");
			}
			
			return new Card(question.toString(),answer.toString());
		}
	}
	private static int distanceToRoot(Token token,
	                                  Token root,
	                                  ArrayList<Token>[] adj,
	                                  AnalyzeSyntaxResponse response,
	                                  int dist) {
		if (adj[response.getTokensList().indexOf(root)].contains(token)) return dist;
		for (Token i : adj[response.getTokensList().indexOf(root)]) {
			distanceToRoot(token, i, adj, response, dist++);
		}
		return Integer.MAX_VALUE;
	}
}
