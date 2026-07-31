package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Centralized authentication helper. Tries to return a token from config first,
 * otherwise will attempt to call an auth endpoint if configured.
 */
public class AuthUtil {

    /**
     * Returns an auth token. Reads auth.token from config if present. Otherwise
     * will attempt a simple POST to auth.url with auth.username and auth.password
     * expecting a JSON field named 'token' or 'accessToken' in the response.
     */
    public static String getAuthToken() {
        String token = ConfigReader.get("auth.token");
        if (token != null && !token.isEmpty()) {
            return token;
        }

        String authUrl = ConfigReader.get("auth.url");
        String username = ConfigReader.get("auth.username");
        String password = ConfigReader.get("auth.password");

        if (authUrl == null || username == null || password == null) {
            return "";
        }

        try {
            String payload = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            Response r = given().contentType("application/json").body(payload).when().post(authUrl);
            if (r.statusCode() >= 200 && r.statusCode() < 300) {
                String t = r.jsonPath().getString("token");
                if (t == null) t = r.jsonPath().getString("accessToken");
                return t == null ? "" : t;
            }
        } catch (Exception e) {
            // swallow and return empty string - token not available
            e.printStackTrace();
        }

        return "";
    }
}

