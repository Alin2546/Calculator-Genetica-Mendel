package com.genetica.genetica_calc.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.genetica.genetica_calc.Model.OpenAI.GeneticsResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeneticsResponse analyzeGenetics(String problemStatement) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = "Extrage alelele și genotipurile părinților din următoarea problemă genetică și returnează într-un JSON cu câmpurile: fatherGenotype, motherGenotype, alleles. Problemă: " + problemStatement;
        ObjectNode requestJson = objectMapper.createObjectNode();
        requestJson.put("model", "gpt-3.5-turbo");
        ArrayNode messages = requestJson.putArray("messages");
        ObjectNode message = messages.addObject();
        message.put("role", "user");
        message.put("content", prompt);


        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(objectMapper.writeValueAsString(requestJson), MediaType.parse("application/json")))
                .build();


        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Eroare OpenAI API: " + response);
        }


        JsonNode root = objectMapper.readTree(response.body().string());
        String content = root.path("choices").get(0).path("message").path("content").asText();


        return objectMapper.readValue(content, GeneticsResponse.class);
    }
}
