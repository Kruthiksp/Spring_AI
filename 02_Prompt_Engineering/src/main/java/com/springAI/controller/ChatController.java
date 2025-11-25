package com.springAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/ai")
@RestController
public class ChatController {

	private final ChatClient chatClient;

	@GetMapping("/generate")
	public ResponseEntity<String> generate(@RequestParam String userPrompt) {
		String response = chatClient.prompt().user(userPrompt).call().content();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// System Prompt will be used to specify the things in the response
	@GetMapping("/generateStuffData")
	public ResponseEntity<String> generateStuffData(@RequestParam String userPrompt) {
		String systemPrompt = """
					if the question is about banks provide them with a particular bank's website link
					if the question is about test cricket then answer the question first and then provide a list of countries play test cricket
				""";
		String response = chatClient.prompt().system(systemPrompt).user(userPrompt).call().content();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/generateSupport")
	public ResponseEntity<String> generateSupport(@RequestParam String userPrompt) {
		String systemPrompt = """
					you can only respond to the questions related to cricket and banking
					for anything else please respond that you are not allowed to answer that topic
				""";
		String response = chatClient.prompt().system(systemPrompt).user(userPrompt).call().content();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
