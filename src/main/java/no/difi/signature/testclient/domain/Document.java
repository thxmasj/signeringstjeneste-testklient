package no.difi.signature.testclient.domain;

import javax.persistence.*;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	private String title;
	
	private String filename;
	
	private String mimetype;

	public Long getId() {
		return id;
	}

	public byte[] getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public String getFilename() {
		return filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public static Builder builder() {
		return new Builder(new Document());
	}

	public static class Builder {
		private Document instance;

		public Builder(Document instance) {
			this.instance = instance;
		}

		public Builder content(byte[] content) {
			instance.content = content;
			return this;
		}

		public Builder title(String title) {
			instance.title = title;
			return this;
		}

		public Builder mimeType(String mimeType) {
			instance.mimetype = mimeType;
			return this;
		}

		public Builder fileName(String fileName) {
			instance.filename = fileName;
			return this;
		}

		public Document build() {
			Document build = instance;
			instance = null;
			return build;
		}

	}


}
