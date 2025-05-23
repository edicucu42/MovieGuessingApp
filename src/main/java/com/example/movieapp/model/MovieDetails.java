package com.example.movieapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class MovieDetails  {
    @JsonProperty("title")
    public String title;
    /*@JsonProperty("vote_count")
    public String vote_count;
    @JsonProperty("runtime")
    public String runtime;
    @JsonProperty("poster_path")
    public String poster_path;*/
    @JsonProperty("overview")
    public String overview;

   //public int id;
}