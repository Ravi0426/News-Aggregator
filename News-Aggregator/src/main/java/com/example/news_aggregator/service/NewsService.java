package com.example.news_aggregator.service;

import com.example.news_aggregator.model.NewsResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import java.util.List;
//import java.util.stream.Collectors;

@Service
public class NewsService {

    //private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Value("${news.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to get news based on the query and category using top-headlines endpoint
    public NewsResponse getNews(String query, String country, String category) {
        // Construct the API URL for top headlines
    	String apiUrl = "https://newsapi.org/v2/top-headlines?q=" + query +
                "&country=" + country +  
                "&category=" + category + 
                "&apiKey=" + apiKey;
    	// Optional: If you want to specify the country

        // Fetch the response from the News API
        return restTemplate.getForObject(apiUrl, NewsResponse.class);
    }
}








