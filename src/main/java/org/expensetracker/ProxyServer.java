package org.expensetracker;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ProxyServer {
    private final int port;
    private final String origin;
    private final CacheManager cacheManager;

    public ProxyServer(int port, String origin, CacheManager cacheManager) {
        this.port = port;
        this.origin = origin;
        this.cacheManager = cacheManager;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new ProxyServerHandler(cacheManager, origin));
        server.setExecutor(null);
        server.start();
    }
}
