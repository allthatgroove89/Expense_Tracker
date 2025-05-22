package com.expensetracker;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    void testSecretRetrieval() {
        JsonObject secretData = App.getSecret();
        assertNotNull(secretData);
        assertTrue(secretData.has("db_user"));
        assertTrue(secretData.has("db_pass"));
    }
}
