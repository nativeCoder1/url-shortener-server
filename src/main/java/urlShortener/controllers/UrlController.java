package urlShortener.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlShortener.dto.requests.ShortenUrlRequest;
import urlShortener.services.UrlService;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {
    @Autowired
    private UrlService urlService;
    @PostMapping("/post_url")
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenUrlRequest shortenUrlRequest){
        return new ResponseEntity<>(urlService.shortenUrl(shortenUrlRequest), HttpStatus.OK);
    }
    @GetMapping("/url/{shortenedUrl}")
    public void getOriginalUrl(@PathVariable String shortenedUrl,  HttpServletResponse response) throws IOException {
         response.sendRedirect(urlService.getUrl(shortenedUrl).getOriginalUrl());
    }
}
