package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
/*
public class WebScraper {
    //SELENIUM when a page has elements of javascript which cannot be accessed by simply reading the html file

    public static double calculateInstagramScore(String instagramUsername) {
        String instagramUrl = "https://www.instagram.com/" + instagramUsername + "/";

        try {
            // Collegamento alla pagina Instagram con un controllo dello stato della risposta
            Connection.Response response = Jsoup.connect(instagramUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .timeout(5000)
                    .ignoreHttpErrors(true) // Ignorare gli errori HTTP
                    .execute();

            if (response.statusCode() == 404) {
                // La pagina non esiste
                System.out.println("Instagram page not found for user: " + instagramUsername);
                return 0.5;
            }

            // Carica il documento HTML solo se la risposta è OK
            Document doc = response.parse();

            // Verifica se l'account è pubblico o privato
            Element scriptTag = doc.select("script[type=application/ld+json]").first();
            if (scriptTag == null) {
                // Nessun tag JSON trovato, potrebbe indicare un account inesistente
                return 0;
            }

            String json = scriptTag.html();
            boolean isPrivate = json.contains("\"is_private\":true");
            if (isPrivate) {
                return 1.0; // Account privato
            }

            // Estrarre data dell'ultimo post
            Element postMeta = doc.select("meta[property=og:description]").first();
            if (postMeta != null) {
                String content = postMeta.attr("content");
                // Aggiungi logica per analizzare la data dei post recenti
                System.out.println("Profile description: " + content);
                // Verifica la data dei post e assegna il punteggio appropriato
                // Per ora assegniamo 2.0 per account pubblico
                return 2.0;
            }

            return 2.5; // Account pubblico ma senza metadati utili
        } catch (IOException e) {
            // Gestione errori di connessione
            System.out.println("Error accessing Instagram for user: " + instagramUsername);
            e.printStackTrace();
            return 0.0; // Assegniamo il punteggio 0 in caso di errore
        }
    }*/


//this is in fact the code of a web crawler not a scraper, since this
public class WebScraper {
    private final HttpClient httpClient;
    private final Set<String> visitedUrls; // Keep track of visited URLs
    private final Queue<String> urlQueue;  // Queue for URLs to visit
    private final int maxDepth;

    public WebScraper(int maxDepth) {
        this.httpClient = HttpClient.newHttpClient();
        this.visitedUrls = new HashSet<>();
        this.urlQueue = new LinkedList<>();
        this.maxDepth = maxDepth; // Maximum depth for crawling
    }

    public void startCrawling(String startUrl) {
        urlQueue.add(startUrl);

        while (!urlQueue.isEmpty()) {
            String currentUrl = urlQueue.poll();
            if (visitedUrls.contains(currentUrl)) {
                continue; // Skip already visited URLs
            }

            System.out.println("Crawling: " + currentUrl);
            visitedUrls.add(currentUrl);

            try {
                String html = fetchHtml(currentUrl);
                parseAndAddUrls(html, currentUrl);
            } catch (IOException | InterruptedException e) {
                System.err.println("Failed to fetch URL: " + currentUrl);
            }

            // Stop crawling if the depth limit is reached
            if (visitedUrls.size() >= maxDepth) {
                System.out.println("Reached maximum crawl depth of " + maxDepth);
                break;
            }
        }

        System.out.println("Crawling complete. Visited " + visitedUrls.size() + " URLs.");
    }

    private String fetchHtml(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Failed to fetch URL. HTTP Status: " + response.statusCode());
        }

        return response.body();
    }

    private void parseAndAddUrls(String html, String baseUrl) {
        Document document = Jsoup.parse(html, baseUrl); // Parse HTML with JSoup
        for (Element link : document.select("a[href]")) {
            String absUrl = link.absUrl("href");
            if (!visitedUrls.contains(absUrl) && isValidUrl(absUrl)) {
                urlQueue.add(absUrl); // Add valid and unvisited URLs to the queue
            }
        }
    }

    private boolean isValidUrl(String url) {
        // Basic validation for URLs
        return url.startsWith("http") && !url.contains("#") && !url.contains("mailto:");
    }
}

