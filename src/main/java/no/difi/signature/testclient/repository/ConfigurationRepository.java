package no.difi.signature.testclient.repository;

import no.difi.signature.testclient.domain.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

}
