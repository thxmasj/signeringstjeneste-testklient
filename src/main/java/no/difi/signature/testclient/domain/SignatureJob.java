package no.difi.signature.testclient.domain;

import no.difi.signature.testclient.validation.Ssn;

import javax.persistence.*;

@Entity
public class SignatureJob {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Ssn
	private String ssn;

	@OneToOne(cascade = CascadeType.ALL)
	private Document document;

	@OneToOne(cascade = CascadeType.ALL)
	private Document signedDocument;

	private String title;

	private String insensitiveTitle;

	private String statusUrl;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] asic;

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

	public Document getSignedDocument() {
		return signedDocument;
	}

	public void setSignedDocument(Document signedDocument) {
		this.signedDocument = signedDocument;
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

	public String getStatusUrl() {
		return statusUrl;
	}

	public void setStatusUrl(String statusUrl) {
		this.statusUrl = statusUrl;
	}
}
