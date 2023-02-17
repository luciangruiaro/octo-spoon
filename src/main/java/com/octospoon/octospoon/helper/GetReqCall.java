package com.octospoon.octospoon.helper;


import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GetReqCall {

    private static final String FAILURE_MESSAGE = "Failed to read HTTP Response.";
    private static final String REQ_PROP_CONTENT_TYPE_KEY = "Content-Type";
    private static final String REQ_PROP_CONTENT_TYPE_VAL_JSON = "application/json";

    public static String restCall(String telegramMethod, JSONObject params) throws Exception {

        HttpURLConnection httpConn = (HttpURLConnection) (new URL(telegramMethod)).openConnection();
        httpConn.setRequestMethod(String.valueOf(HttpMethod.GET));
        httpConn.setRequestProperty(REQ_PROP_CONTENT_TYPE_KEY, REQ_PROP_CONTENT_TYPE_VAL_JSON);
        httpConn.setDoOutput(true);

        try (OutputStream os = httpConn.getOutputStream()) {
            byte[] input = params.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        }
        httpConn.disconnect();
        throw new Exception(FAILURE_MESSAGE);
    }

}
