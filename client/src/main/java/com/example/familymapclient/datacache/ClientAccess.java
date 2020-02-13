package com.example.familymapclient.datacache;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientAccess {


    public String getUrl(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", DataCache.getInstance().authToken);

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                return outputStream.toString();
            }
        } catch (Exception e) {
            Log.e("ClientAccess", e.getMessage(), e);
        }

        return null;
    }

    public String postUrl(URL url, String requestBody) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(5000);

            connection.connect();

            try(OutputStream reqBody = connection.getOutputStream()) {
                reqBody.write(requestBody.getBytes());
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                return outputStream.toString();
            }
        } catch (Exception e) {
            Log.e("ClientAccess", e.getMessage(), e);
        }

        return null;
    }
}
