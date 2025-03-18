package org.expensetracker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ProxyServerHandler implements HttpHandler {
    private final CacheManager cacheManager;
    private final String origin;

    public ProxyServerHandler(CacheManager cacheManager, String origin) {
        this.cacheManager = cacheManager;
        this.origin = origin;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestUrl = origin + exchange.getRequestURI().toString();
        String method = exchange.getRequestMethod();
        String response = "";

        try {
            if (method.equalsIgnoreCase("GET") && cacheManager.isCached(requestUrl)) {
                response = cacheManager.getFromCache(requestUrl);
                exchange.getResponseHeaders().add("X-Cache", "HIT");
            } else {
                response = forwardRequest(requestUrl);

                if (method.equalsIgnoreCase("GET")) {
                    cacheManager.addToCache(requestUrl, response);
                }

                exchange.getResponseHeaders().add("X-Cache", "MISS");
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (IOException e) {
            System.out.println("Error handling request: " + e.getMessage());
            if (exchange.getResponseCode() == -1) {
                exchange.sendResponseHeaders(500, 0);
            }
            exchange.close();
        }
    }


    private String forwardRequest(String requestUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
             Scanner scanner = new Scanner(inputStream)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
