package urlShortener.services;

import urlShortener.data.models.UrlLink;
import urlShortener.dto.requests.ShortenUrlRequest;
import urlShortener.dto.responses.ShortenUrlResponse;

public interface UrlService {
    ShortenUrlResponse shortenUrl(ShortenUrlRequest shortenUrlRequest);
    UrlLink getUrl(String shortenedUrl);
}
