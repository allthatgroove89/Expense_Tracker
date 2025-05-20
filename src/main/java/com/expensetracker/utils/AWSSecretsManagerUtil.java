package com.expensetracker.utils;

import com.amazonaws.services.secretsmanager.*;
import com.amazonaws.services.secretsmanager.model.*;
import com.fasterxml.jackson.databind.*;
import java.util.Map;

public class AWSSecretsManagerUtil {
    public static Map<String, String> getSecretValue(String secretId) {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(secretId);
        GetSecretValueResult result = client.getSecretValue(request);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result.getSecretString(), Map.class);
    }
}
