package pl.wiktor.learning.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    Document getByStartAtBeforeAndEndAtAfterAndTo_IdEquals(Instant startAt, Instant endAt, String to_id);
    List<Document> getAllByStartAtBeforeAndEndAtBeforeAndTo_IdEqualsAndDocumentStatusEquals(LocalDate startAt, LocalDate endAt, String to_id, DocumentStatus documentStatus);
}
