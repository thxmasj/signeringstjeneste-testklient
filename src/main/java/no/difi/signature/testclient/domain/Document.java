package no.difi.signature.testclient.domain;

import javax.persistence.*;

@Entity
public class Document {

	@Id
	@GeneratedValue
	private Long id;


	@ManyToOne
	private Signature signature;


	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	private String title;
	
	private String filename;
	
	private String mimetype;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public Signature getSignature() {
		return signature;
	}


	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	
}
