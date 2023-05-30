package urlShortener.services;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlShortener.data.models.UrlLink;
import urlShortener.dto.requests.ShortenUrlRequest;
import urlShortener.dto.responses.ShortenUrlResponse;
import urlShortener.data.repository.UrlRepository;
import urlShortener.exceptions.InvalidUrlException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
@Service
public class UrlServiceImplementation implements UrlService {
    @Autowired
    private UrlRepository urlRepository;
    @Override
    public ShortenUrlResponse shortenUrl(ShortenUrlRequest shortenUrlRequest) {
        UrlLink newUrl = buildUrl(shortenUrlRequest);
        UrlLink savedUrl = urlRepository.save(newUrl);
        ShortenUrlResponse shortenUrlResponse = buildResponse(savedUrl);
        return shortenUrlResponse;
    }

    @Override
    public UrlLink getUrl(String shortenedUrl) {
        UrlLink foundUrl = urlRepository.findUrlLinkByShortenedUrl(shortenedUrl);
        return foundUrl;
    }

    private UrlLink buildUrl(ShortenUrlRequest shortenUrlRequest) {
        UrlLink newUrl = new UrlLink();
        LocalDateTime localDateTime = LocalDateTime.now();
        if ((!shortenUrlRequest.getUrl().startsWith("http")) || (!shortenUrlRequest.getUrl().startsWith("https"))
                || shortenUrlRequest.getUrl().contains(" ") || shortenUrlRequest.getUrl().length() == 0){
            throw new InvalidUrlException("Invalid url");
        }
        String hashedUrl = getShortenedUrl(shortenUrlRequest, localDateTime);
        newUrl.setOriginalUrl(shortenUrlRequest.getUrl());
        newUrl.setShortenedUrl(hashedUrl);
        return newUrl;
    }

    private String getShortenedUrl(ShortenUrlRequest shortenUrlRequest, LocalDateTime localDateTime) {
        return Hashing.murmur3_32()
                .hashString(shortenUrlRequest.getUrl().concat(localDateTime.toString()), StandardCharsets.UTF_8)
                .toString();
    }

    private ShortenUrlResponse buildResponse(UrlLink urlLink){
        ShortenUrlResponse shortenUrlResponse = new ShortenUrlResponse();
        if (urlLink.getOriginalUrl().contains(" ")){
            shortenUrlResponse.setStatusCode(400);
            shortenUrlResponse.setMessage("Invalid url");
        }
        shortenUrlResponse.setUrlId(urlLink.getUrlId());
        shortenUrlResponse.setOriginalUrl(urlLink.getOriginalUrl());
        shortenUrlResponse.setShortenedUrl(urlLink.getShortenedUrl());
        shortenUrlResponse.setStatusCode(200);
        shortenUrlResponse.setMessage("Url shortened sucessfully");
        return shortenUrlResponse;
    }
}