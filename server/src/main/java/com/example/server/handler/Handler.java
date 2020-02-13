package com.example.server.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.example.shared.result.Result;

import java.io.*;
import java.net.HttpURLConnection;

public abstract class Handler {
    void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public void closeOnCatch(HttpExchange httpExchange, Exception e) throws IOException {
        // if things go wrong
//        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //**************************************** changed from original!!!!!!!!
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        Result result = new Result();
        result.setMessage("Error " + e.toString());

        // convert result to json
        Gson gsonRes = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gsonRes.toJson(result);

        // Write result out to body
        OutputStream respBody = httpExchange.getResponseBody();
        writeString(jsonString, respBody);

        respBody.close();
        e.printStackTrace();
    }
}
