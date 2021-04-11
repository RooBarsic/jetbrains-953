package com.company.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestUtils {

    /** Method to make quick GET request and get the JSON response data*/
    public static String httpsGETRequest(@NotNull final String urlPath) throws IOException {
        try {
            final HttpURLConnection con;
            final URL myUrl = new URL(urlPath);
            con = (HttpURLConnection) myUrl.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            final StringBuilder content = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                br.lines().forEach( line -> {
                    content.append(line);
                    content.append(System.lineSeparator());
                });

            } catch (Exception ignored) {
                throw new IOException();
            }

            return content.toString();
        } catch (MalformedURLException | ProtocolException ignored) {
            throw new IOException();
        }
    }}
