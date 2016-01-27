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

	//@Size(min = 1, message = "Du må oppgi tittel.")
	//@NotNull
	private String title;

	@Document(message = "Du må oppgi hoveddokument.")
	private MultipartFile document;

	//@NotNull
	private String senderOrgNumber;
	
	private String senderId;

	//@NotNull
	private String keyPairAlias;


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

	public SignatureCommand() {
	}

	public MultipartFile getDocument() {
		return document;
	}

	public void setDocument(MultipartFile document) {
		this.document = document;
	}

	public String getSenderOrgNumber() {
		return senderOrgNumber;
	}

	public void setSenderOrgNumber(String senderOrgNumber) {
		this.senderOrgNumber = senderOrgNumber;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	
	public String getKeyPairAlias() {
		return keyPairAlias;
	}

	public void setKeyPairAlias(String keyPairAlias) {
		this.keyPairAlias = keyPairAlias;
	}

}
