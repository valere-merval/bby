package de.congstar.congo.aax4.rest;

import de.congstar.congo.rest.aax4.client.api.ApiClient;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CoRestService {
    private static final ConcurrentMap<String, String> customerToToken = new ConcurrentHashMap<>();
    private static final HttpRequest.Builder builder;
    static {
        builder = HttpRequest.newBuilder();
        builder.header("Content-Type", "application/x-www-form-urlencoded");
        builder.header("Accept", "application/json");
    }

    @SneakyThrows
    public static String createAuthenticationToken(String baseUrl, String username, String password) {
        return customerToToken.computeIfAbsent(username, cred -> {
            builder.uri(URI.create(baseUrl + "/co-restservice/authenticate"));
            builder.method("POST", HttpRequest.BodyPublishers.ofString("username=" + username + "&password=" + password));
            try {
                var apiClient = new ApiClientFactory(baseUrl).getDefaultClient();
                var response = apiClient.getHttpClient().send(builder.build(), HttpResponse.BodyHandlers.ofString());
                var responseObject = apiClient.getObjectMapper().readValue(response.body(), AuthenticationResponse.class);
                return responseObject.getAccess_token();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SneakyThrows
    public static <T> T getControllerApi(ApiClient apiClient, Class<T> apiClass) {
        return apiClass.getConstructor(ApiClient.class).newInstance(apiClient);
    }

    @Data
    public static class AuthenticationResponse {
        private String access_token;
    }
}
