package no.difi.signature.testclient.repository;

import no.difi.signature.testclient.domain.Signature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SignatureRepository extends JpaRepository<Signature, Long> {

	public List<Signature> findByConversationId(String conversationId);

	public List<Signature> findById(String Id);


	//@Query("select s.id as id, s.ssn, from Signature s order by s.id desc")
	//public Page<Object[]> list(Pageable pageable);

	/*
	@Query("select m.id as id, m.date as date, m.ssn, m.document.title, m.digital, m.fysiskPost.adressat.navn from Message m order by m.id desc")
	public Page<Object[]> list(Pageable pageable);
	*/

}
