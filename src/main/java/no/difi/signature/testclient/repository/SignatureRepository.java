package no.difi.signature.testclient.repository;

import no.difi.signature.testclient.domain.Signature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SignatureRepository extends JpaRepository<Signature, Long> {

	public List<Signature> findById(String Id);

	@Query("select s.id as id, s.ssn as ssn, s.title as title from Signature s")
	public Page<Object[]> list(Pageable pageable);


}
