package com.example.movieapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class MovieList  {
    @JsonProperty("page")
    public String page;
    @JsonProperty("total_pages")
    public String total_pages;
    @JsonProperty("total_results")
    public String total_results;
    @JsonProperty("results")
    public MovieDetails[] results;


}