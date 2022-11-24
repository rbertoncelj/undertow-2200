package com.example.undertow2200;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PathCheckServletIT {

    /**
     * Verifies that expression filter has been correctly configured before integration tests
     */
    @Test
    public void checkFilterSetup() throws Exception {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/foo/test"))
                .GET()
                .build();

        final String observedPath = HttpClient.newHttpClient()
                .send(request, BodyHandlers.ofString())
                .body();

        Assertions.assertEquals("/bar/test", observedPath);
    }

    /**
     * Verifies that PathCheckServlet functions correctly when accessed directly
     */
    @Test
    public void withoutRedirect() throws Exception {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/bar/hello%2Fworld"))
                .GET()
                .build();

        final String observedPath = HttpClient.newHttpClient()
                .send(request, BodyHandlers.ofString())
                .body();

        Assertions.assertEquals("/bar/hello%2Fworld", observedPath);
    }

    /**
     * Verifies that undertow's rewrite handling does not change the remaining path.
     * <p>
     * This is an actual reproducer of UNDERTOW-2200 issue!
     */
    @Test
    public void withRedirect() throws Exception {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/foo/hello%2Fworld"))
                .GET()
                .build();

        final String observedPath = HttpClient.newHttpClient()
                .send(request, BodyHandlers.ofString())
                .body();

        Assertions.assertEquals("/bar/hello%2Fworld", observedPath);
    }
}