package com.example.movieapp.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.movieapp.model.MovieList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveMovieDetailsService {

    @Value("${TMDB_API_KEY}")
    private String tmdbApiKey;

    public MovieList getDataSetForCurrentDay(int page) throws IOException, InterruptedException{;
        URL obj = new URL("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page="+ page+tmdbApiKey);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            // print result
            ObjectMapper objectMapper = new ObjectMapper();
            //allows for the usage of the api without implementing all offered characteristics
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            //System.out.println("!!!!!!!!!!!!!!!!!!!!!"+response);
            MovieList rootData =objectMapper.readValue(response.toString(), MovieList.class);
            return rootData;
        } else {
            System.out.println("GET request not worked");
        }

        for (int i = 1; i <= 8; i++) {
            System.out.println(httpURLConnection.getHeaderFieldKey(i) + " = " + httpURLConnection.getHeaderField(i));
        }
        return null;

    }




}
