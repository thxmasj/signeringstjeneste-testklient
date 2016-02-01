package no.difi.signature.testclient.repository;

import no.difi.signature.testclient.domain.SignatureJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SignatureRepository extends JpaRepository<SignatureJob, Long> {

	public List<SignatureJob> findById(String Id);

	@Query("select s.id as id, s.ssn as ssn, s.title as title from SignatureJob s")
	public Page<Object[]> list(Pageable pageable);


}
