# Caching Proxy Server

This project is a CLI-based Caching Proxy Server built in Java.

## Features
- Acts as a caching proxy server that forwards requests to the specified origin server.
- Caches the responses to minimize network traffic and reduce server load.
- Distinguishes between cache hits and cache misses using the following headers:
    - `X-Cache: HIT` — Response served from the cache.
    - `X-Cache: MISS` — Response fetched from the origin server.
- Supports clearing the cache via a simple command.

## How It Works
- The user starts the server by specifying the port and the origin URL.
- When a request is made to the caching proxy server, it checks if the response is already cached.
    - If cached, the response is served with the header `X-Cache: HIT`.
    - If not cached, the request is forwarded to the origin server, the response is cached, and the header `X-Cache: MISS` is added.
- Cached data can be cleared through a command.

## Getting Started
### Prerequisites
- Java JDK installed (version 8 or higher recommended)

### Running the Server
Compile and run the application:
```bash
javac CachingProxyServer.java
java CachingProxyServer
```

### Input Configuration
- You will be prompted to enter the port number and the origin server URL via the terminal.

### Example Usage
1. Start the server:
```
Enter the port number: 3000
Enter the origin server URL: http://dummyjson.com
```
2. Make a request:
```
http://localhost:3000/products
```
3. Clear the cache:
```
Type '--clear-cache' in the terminal to clear the cache.
```

## Response Headers
- `X-Cache: HIT` — Indicates the response is from the cache.
- `X-Cache: MISS` — Indicates the response is fetched from the origin server.

## Future Improvements
- Support for more complex request methods (POST, PUT, DELETE).
- Advanced cache expiration and invalidation strategies.
- Configurable cache size limits.
