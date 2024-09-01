package de.congstar.congo.aax4.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.congstar.congo.rest.aax4.client.api.ApiClient;
import lombok.Getter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ApiClientFactory {
    private static final ConcurrentMap<String, ApiClient> tokenToClient = new ConcurrentHashMap<>();

    private final String scheme;
    private final String host;

    @Getter
    private final ApiClient defaultClient;


    public ApiClientFactory(String baseUrl) {
        try {
            var aax4BackendBaseUrl = new URL(baseUrl);
            scheme = aax4BackendBaseUrl.getProtocol();
            host = aax4BackendBaseUrl.getHost();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        defaultClient = createApiClient(null);
    }

    public ApiClient getApiClient(String token) {
        return tokenToClient.computeIfAbsent(token, this::createApiClient);
    }

    private ApiClient createApiClient(String token) {
        var client = new ApiClient();
        // Scheme + Host
        client.setScheme(scheme);
        client.setHost(host);
        // Mapper module
        var mapper = client.getObjectMapper().registerModule(
                new SimpleModule().addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer())
        );
        client.setObjectMapper(mapper);
        // Bearer token
        if (token != null) {
            client.setRequestInterceptor(builder -> builder.header("Authorization", "Bearer " + token));
        }
        return client;
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            String dateAsString = jsonParser.getText();
            if (dateAsString == null) {
                throw new IOException("OffsetDateTime argument is null.");
            }
            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(dateAsString, DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeParseException e) {
                try {
                    dateTime = LocalDateTime.parse(dateAsString, DATE_TIME_FORMATTER);
                } catch (DateTimeParseException e2) {
                    dateTime = LocalDate.parse(dateAsString, DATE_FORMATTER).atStartOfDay();
                }
            }
            return dateTime;
        }
    }
}
