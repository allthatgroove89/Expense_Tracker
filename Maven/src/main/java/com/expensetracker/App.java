package com.expensetracker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class App {
    public static void main(String[] args) {
        try {
            JsonObject secretData = getSecret();
            String username = secretData.get("db_user").getAsString();
            String password = secretData.get("db_pass").getAsString();
            System.out.println("Using DB Username: " + username);
            System.out.println("Retrieved Secret Data: " + secretData);
        } catch (Exception e) {
            System.err.println("Failed to retrieve secret: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static JsonObject getSecret() {
        String secretName = "expense_tracker";
        Region region = Region.of("us-east-1");
        SecretsManagerClient client = SecretsManagerClient.builder().region(region).build();

        GetSecretValueRequest request = GetSecretValueRequest.builder().secretId(secretName).build();
        GetSecretValueResponse response = client.getSecretValue(request);

        return JsonParser.parseString(response.secretString()).getAsJsonObject();
    }
}
