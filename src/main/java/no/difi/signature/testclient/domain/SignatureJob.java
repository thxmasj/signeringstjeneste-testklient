package no.difi.signature.testclient.domain;

import no.difi.signature.testclient.validation.Ssn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class SignatureJob {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Ssn
	private String ssn;

	@OneToOne(cascade = CascadeType.ALL)
	private Document document;

	private String title;

	private String insensitiveTitle;


	public SignatureJob() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInsensitiveTitle() {
		return insensitiveTitle;
	}

	public void setInsensitiveTitle(String insensitiveTitle) {
		this.insensitiveTitle = insensitiveTitle;
	}

}
