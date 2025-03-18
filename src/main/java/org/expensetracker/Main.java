package org.expensetracker;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final CacheManager cacheManager = new CacheManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter '--clear-cache' to clear cache or '--start' to start the proxy server:");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("--clear-cache")) {
            cacheManager.clearCache();
            System.out.println("Cache cleared successfully.");
        } else if (input.equalsIgnoreCase("--start")) {
            System.out.println("Enter port number for the proxy server:");
            int port = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the origin server URL:");
            String origin = scanner.nextLine();

            startProxyServer(port, origin);
        } else {
            System.out.println("Invalid input. Use '--clear-cache' or '--start'.");
        }

        scanner.close();
    }

    private static void startProxyServer(int port, String origin) {
        try {
            ProxyServer server = new ProxyServer(port, origin, cacheManager);
            server.start();
            System.out.println("Caching Proxy Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Failed to start the server: " + e.getMessage());
        }
    }
}
