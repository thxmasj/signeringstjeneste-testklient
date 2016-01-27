package no.difi.signature.testclient.repository;

import no.difi.signature.testclient.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
