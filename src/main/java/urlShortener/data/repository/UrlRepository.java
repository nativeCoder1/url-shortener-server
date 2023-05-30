package urlShortener.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import urlShortener.data.models.UrlLink;

public interface UrlRepository extends JpaRepository<UrlLink, Long> {
    UrlLink findUrlLinkByShortenedUrl(String url);
}