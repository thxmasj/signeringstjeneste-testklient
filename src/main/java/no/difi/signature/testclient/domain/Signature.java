package no.difi.signature.testclient.domain;

import no.difi.signature.testclient.validation.Ssn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class Signature {

	@Id
	@GeneratedValue
	private Long id;

	@Ssn
	private String ssn;

	@OneToOne(cascade = CascadeType.ALL)
	private Document document;

	private String title;

	private String insensitiveTitle;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] asic;

	public Signature() {
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

	public byte[] getAsic() {
		return asic;
	}

	public void setAsic(byte[] asic) {
		this.asic = asic;
	}
}
