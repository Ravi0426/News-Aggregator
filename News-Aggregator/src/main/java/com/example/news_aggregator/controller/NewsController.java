package com.example.news_aggregator.controller;

import com.example.news_aggregator.model.NewsResponse;
import com.example.news_aggregator.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Method to handle default page load
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("country", "");
        model.addAttribute("category", "");
        model.addAttribute("query", "");
        model.addAttribute("news", new ArrayList<>());
        return "index";
    }

    // Method to get news based on user input
    @GetMapping("/news")
    public String getNews(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "category", required = false) String category,
            Model model) {

        // If any of the required parameters are missing, return an error message
        if (query == null || country == null || category == null) {
            model.addAttribute("message", "Please enter all required fields!");
            model.addAttribute("country", country);
            model.addAttribute("category", category);
            model.addAttribute("query", query);
            model.addAttribute("news", new ArrayList<>());
            return "index";  // Prevents empty requests
        }

        // Fetch the news for the given query, country, and category
        NewsResponse newsResponse = newsService.getNews(query, country, category);

        // Ensure news is only added once and handle null cases
        if (newsResponse != null && newsResponse.getArticles() != null) {
            model.addAttribute("news", newsResponse.getArticles());
        } else {
            model.addAttribute("news", new ArrayList<>());
        }

        // Passing the parameters to keep the form populated for user convenience
        model.addAttribute("query", query);
        model.addAttribute("country", country);
        model.addAttribute("category", category);

        return "index";
    }
}
