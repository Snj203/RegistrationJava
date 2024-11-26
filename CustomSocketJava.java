package org.example;
import javax.net.ssl.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomSocketJava {
    private String host;
    private int port;

    public CustomSocketJava(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String sendGetRequest(String route, SSLContext sslContext) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException, KeyStoreException {
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(keyStore);
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        try (SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(host, port)) {
            sslSocket.startHandshake();

            String request = "GET " + route + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Connection: close\r\n\r\n";

            OutputStream outputStream = sslSocket.getOutputStream();
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            return readResponse(sslSocket);
        }
    }

    public String sendPostRequest(String route, String urlencode, SSLContext sslContext) throws NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException, KeyStoreException {
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(keyStore);
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, tmf.getTrustManagers(), null);

        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        try (SSLSocket sslSocket = (SSLSocket) socketFactory.createSocket(host, port)) {
            sslSocket.startHandshake();

            String request =
                    "POST /login/signup.php HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Content-Type: application/x-www-form-urlencoded; charset=UTF-8;\r\n" +
                    "Content-Length: " + urlencode.length() + "\r\n" +
                    "Cookie: MoodleSession=" + extractMoodleSession("index.html") + "\r\n" +
                    "Connection: close\r\n\r\n" +
                    urlencode;

            OutputStream outputStream = sslSocket.getOutputStream();
            outputStream.write(request.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            return readResponse(sslSocket);
        }
    }

    private String readResponse(SSLSocket sslSocket) throws IOException, InterruptedException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.lineSeparator());
            }
        }
        saveToFile(response.toString(), "index.html");

        return response.toString();
    }

    private void saveToFile(String content, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    public String extractMoodleSession(String response) throws IOException {

        String sessionId = "not found";
        Pattern pattern = Pattern.compile("Set-Cookie: MoodleSession=([^;]+);");
        String match = readFileAsString(response);
        Matcher matcher = pattern.matcher(match);

        if (matcher.find()) {
            sessionId = matcher.group(1); // Извлекаем значение MoodleSession
        }
        System.out.println("Cookie найден: " + sessionId);
        return sessionId;
    }

    public String getSesskeyFromHTML(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        Pattern pattern = Pattern.compile("\"sesskey\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(content.toString());
        return matcher.find() ? matcher.group(1) : "not found sesskey";
    }

    public static String readFileAsString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString().trim();
    }
}
