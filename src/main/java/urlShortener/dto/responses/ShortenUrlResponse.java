package urlShortener.dto.responses;

import lombok.Data;

@Data
public class ShortenUrlResponse {
    private Long urlId;
    private int statusCode;
    private String message;
    private String originalUrl;
    private String shortenedUrl;
}
