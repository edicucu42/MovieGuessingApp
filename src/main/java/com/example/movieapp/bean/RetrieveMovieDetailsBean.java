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


    private void loadRandomMovie() throws IOException, InterruptedException {
        // Choose a random day (between 1 and 10)
        int randomDay = new Random().nextInt(10) + 1;

        movielist = retrieveMovieDetailsService.getDataSetForCurrentDay(randomDay);
        movie_datalist = movielist.results;

        Random rand = new Random();
        int select_movie = rand.nextInt(movie_datalist.length);
        movie_synopsis = movie_datalist[select_movie].overview;
        correct_movie_title = movie_datalist[select_movie].title;
        //System.out.println(correct_movie_title);

        movie_titles.clear();
        for (int i = 2; i <= 10; i++) {
            movielist = retrieveMovieDetailsService.getDataSetForCurrentDay(i);
            movie_datalist = movielist.results;

            for (MovieDetails m : movie_datalist) {
                movie_titles.add(m.getTitle());
            }
        }

    }

    /**
     * Metoda aceasta se ocupa initializarea formei
     */
    @PostConstruct
    public void init() {
        try {
            loadRandomMovie();
        } catch (IOException | InterruptedException e) {
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
            try {
                loadRandomMovie();
                selectedItem = "";
            } catch (IOException | InterruptedException e) {
                outputText = "Something went wrong while loading the next movie.";
            }
        } else outputText = "Hmm, maybe try again...";
    }
}