package com.example.movieapp.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import com.example.movieapp.model.MovieDetails;
import com.example.movieapp.model.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.movieapp.service.RetrieveMovieDetailsService;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
//@RequiredArgsConstructor
@ViewScoped
@Named
@Getter
@Setter

public class RetrieveMovieDetailsBean implements Serializable {
    @Autowired
    private RetrieveMovieDetailsService retrieveMovieDetailsService ;
    private MovieList movielist;
    private MovieDetails[] movie_datalist;
    private String movie_synopsis;
    @Getter
    private String correct_movie_title;
    @Getter
    private  ArrayList<String> movie_titles = new ArrayList<>();
    private int difficulty;
    private String text;

    private String countryName;
    @Setter
    @Getter
    private String selectedItem;



    @Getter
    private String outputText = "";


    /**
     * Metoda aceasta se ocupa initializarea formei
     */
    @PostConstruct
    public void init() {
        //log.info("init method called");
        try {
            movie_synopsis="poveste";
            movielist = retrieveMovieDetailsService.getDataSetForCurrentDay(1);
            movie_datalist = movielist.results;
            Random rand = new Random();
            int select_movie = rand.nextInt(movie_datalist.length);
            movie_synopsis = movie_datalist[select_movie].overview;
            correct_movie_title = movie_datalist[select_movie].title;
            movie_titles.clear();
            System.out.println(correct_movie_title);


            for (int i = 2; i <= 10; i++) {
                int size = movie_datalist.length;
                for (int j = 0; j < size; j++) {
                    movie_titles.add(movie_datalist[j].getTitle());

                }
                movielist = retrieveMovieDetailsService.getDataSetForCurrentDay(i);
                movie_datalist = movielist.results;
            }


        } catch (IOException  | InterruptedException e) {
            throw new RuntimeException(e);
        }



    }


    public String getCorrectMovieTitle() {
        return this.correct_movie_title;
    }

   public List<String> getMovieList(String query) {
       String queryLowerCase = query.toLowerCase();
       return movie_titles.stream()
               .filter(t -> t.toLowerCase().startsWith(queryLowerCase))
               .collect(Collectors.toList());

   }

    public void processSelection() {
        if (Objects.equals(selectedItem, getCorrectMovieTitle())) {
            outputText = "Good job, you guessed right!!";
        } else outputText = "Hmm, maybe try again...";
    }
}