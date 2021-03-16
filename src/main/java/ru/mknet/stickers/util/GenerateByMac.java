package ru.mknet.stickers.util;


import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Component
public class GenerateByMac {

    public List<String> getData(String macAddress) {

        String url = "https://wwwip.saitovnet.com/routers/setup/" + macAddress;

        List<String> list = null;

        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    inputLine = inputLine.replaceAll("\\[", "");
                    inputLine = inputLine.replaceAll("\"", "");
                    list = Arrays.asList(inputLine.split(","));
                }

                in.close();
            } else {
                System.out.println("GET request dont worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;

    }

}
