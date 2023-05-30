package urlShortener.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import urlShortener.data.models.UrlLink;
import urlShortener.dto.requests.ShortenUrlRequest;
import urlShortener.dto.responses.ShortenUrlResponse;
import urlShortener.exceptions.InvalidUrlException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UrlServiceImplementationTest {
    @Autowired
    private UrlService urlService;
    private ShortenUrlRequest shortenUrlRequest;
    private ShortenUrlRequest shortenUrlRequestTwo;
    private ShortenUrlRequest shortenUrlRequestThree;
    private ShortenUrlRequest shortenUrlRequestFour;
    private String urlLink;
    @BeforeEach
    void setUp() {
        shortenUrlRequest = new ShortenUrlRequest();
        shortenUrlRequestTwo = new ShortenUrlRequest();
        shortenUrlRequestThree = new ShortenUrlRequest();
        shortenUrlRequestFour = new ShortenUrlRequest();
        urlLink = "https://codereview.stackexchange.com/questions/84812/" +
                "java-class-that-generates-a-short-url-string-for-an-input-string";
        shortenUrlRequest.setUrl(urlLink);

        shortenUrlRequestTwo.setUrl("");
        shortenUrlRequestThree.setUrl("https://codereview.stackexchange.com/questions/84812/java-class-that-generates-a-short-url-string-for-an-input-string");
        shortenUrlRequestFour.setUrl("https://codereview.stackexchange.com/questions/84812/java-class-that-generates-a-short-url-string-for-an-input-string");
    }

    @Test
    void testShortenNonEmptyUrl() {
        ShortenUrlResponse shortenUrlResponse = urlService.shortenUrl(shortenUrlRequest);
        assertEquals(shortenUrlResponse.getStatusCode(), 200);
    }
    @Test
    void testEmptyUrlThrowsException(){
        assertThrows(InvalidUrlException.class, () ->urlService.shortenUrl(shortenUrlRequestTwo));
    }

    @Test
    @DisplayName("Test that generated shortened urls are always unique for the same original url")
    void testThatGeneratedUrlsAreAlwaysUnique(){
        String shortenedUrlOne = urlService.shortenUrl(shortenUrlRequestThree).getShortenedUrl();
        String shortenedUrlTwo = urlService.shortenUrl(shortenUrlRequestFour).getShortenedUrl();
        System.out.println(shortenedUrlOne);
        System.out.println(shortenedUrlTwo);
        assertNotEquals(shortenedUrlOne, shortenedUrlTwo);
    }

    @Test
    @DisplayName("Test that shortened urls of the same original urls always give back same original url")
    void testUrlReversibility(){
        ShortenUrlResponse shortenUrlResponseOne = urlService.shortenUrl(shortenUrlRequestThree);
        ShortenUrlResponse shortenUrlResponseTwo  = urlService.shortenUrl(shortenUrlRequestFour);
        System.out.println(shortenUrlResponseOne.getOriginalUrl());
        System.out.println(shortenUrlResponseTwo.getOriginalUrl());
        assertEquals(shortenUrlResponseOne.getOriginalUrl(), shortenUrlResponseTwo.getOriginalUrl());
    }

    @Test
    @DisplayName("Test that original url can be gotten from shortened url")
    void testGetOriginalUrl() {
        ShortenUrlResponse shortenUrlResponse = urlService.shortenUrl(shortenUrlRequest);
        UrlLink url = urlService.getUrl(shortenUrlResponse.getShortenedUrl());
        assertEquals(urlLink, url.getOriginalUrl());
    }
}