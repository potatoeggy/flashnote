package com.example.flashnote.AI;

import com.google.api.client.util.Lists;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetectText {
	static final String jsonPath = "Flashnote-3278311c8d16.json";
	
	protected static ArrayList<String> detectText() throws IOException {
		String filePath = "OCR_TEST2.jpg";
		return detectText(filePath);
	}
	
	// Detects text in the specified image.
	protected static ArrayList<String> detectText(String filePath) throws IOException {
		List<AnnotateImageRequest> requests = new ArrayList<>();
		
		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));
		
		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
		AnnotateImageRequest request =
				AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);
		
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
				                                .createScoped(Lists.newArrayList());
		
		
		// Initialize client that will be used to send requests. This client only needs to be created
		// once, and can be reused for multiple requests. After completing all of your requests, call
		// the "close" method on the client to safely clean up any remaining background resources.
		ImageAnnotatorSettings imageAnnotatorSettings =
				ImageAnnotatorSettings.newBuilder()
						.setCredentialsProvider(FixedCredentialsProvider.create(credentials))
						.build();
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create(imageAnnotatorSettings)) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			ArrayList<String> text = new ArrayList<>();
			text.add("");
			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.format("Error: %s%n", res.getError().getMessage());
					return null;
				}
				
				// For full list of available annotations, see http://g.co/cloud/vision/docs
				for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
					//System.out.format("Text: %s%n", annotation.getDescription());
					text.set(text.size() - 1, text.get(text.size() - 1) + annotation.getDescription() + " ");
					//System.out.format("Position : %s%n", annotation.getBoundingPoly());
				}
				text.set(text.size() - 1, SpellChecker.check(text.get(text.size() - 1)));
				text.add("");
			}
			return text;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}