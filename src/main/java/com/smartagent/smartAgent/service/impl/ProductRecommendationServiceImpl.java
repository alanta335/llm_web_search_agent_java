package com.smartagent.smartAgent.service.impl;

import com.smartagent.smartAgent.utility.CommonUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductRecommendationServiceImpl {
    private final CommonUtility commonUtility;

    public String recommendProducts(String productName) {
        String url = "https://www.amazon.in/s?k=" + productName;
        Document doc = commonUtility.getWebPageContent(url);

        // Fetch all product list items
        Set<String> productLinks = doc.getElementsByAttributeValue("role", "listitem").stream()
                .map(element -> element.select("[data-cy=title-recipe] a[href]"))
                .filter(elements -> elements.size()==1)// Select 'a' tags with href inside 'title-recipe'
                .flatMap(Elements::stream)
                .map(element -> element.attr("href")) // Extracting href attribute
                .collect(Collectors.toSet());

        return "recommendProducts";
    }

}
