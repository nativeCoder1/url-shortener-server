package urlShortener.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UrlLink {
    @Id
    @GeneratedValue
    private Long urlId;
    private String originalUrl;
    private String shortenedUrl;
}