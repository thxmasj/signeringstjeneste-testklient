package no.difi.signature.testclient.web;


import no.difi.signature.testclient.validation.Document;
import no.difi.signature.testclient.validation.Ssn;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class SignatureCommand {


	@Ssn(message = "Ugyldig fødselsnummer.")
	private String ssn;

	@Size(min = 1, message = "Du må oppgi tittel.")
	@NotNull
	private String title;

	@Size(min = 1, message = "Du må oppgi ikke-sensitiv tittel.")
	@NotNull
	private String insensitiveTitle;

	@Document(message = "Du må oppgi hoveddokument.")
	private MultipartFile document;


	public SignatureCommand() {
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
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

	public MultipartFile getDocument() {
		return document;
	}

	public void setDocument(MultipartFile document) {
		this.document = document;
	}



}
