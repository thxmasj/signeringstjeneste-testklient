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


	private String conversationId;


	@OneToOne(cascade = CascadeType.ALL)
	private Document document;

	/*
	@NotNull
	private String senderOrgNumber;

	private String senderId;

	@NotNull
	private String keyPairAlias;



	private boolean saveBinaryContent;

	*/
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

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}


	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document document) {
        this.document = document;
    }
	/*

	
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



	public boolean getSaveBinaryContent() {
		return saveBinaryContent;
	}

	public void setSaveBinaryContent(boolean saveBinaryContent) {
		this.saveBinaryContent = saveBinaryContent;
	}

	*/
}
