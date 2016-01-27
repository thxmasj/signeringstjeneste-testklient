package no.difi.signature.testclient.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Configuration {

	@Id
	private Long id;

	@NotNull
	private String signaturePartitionChannel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSignaturePartitionChannel() {
		return signaturePartitionChannel;
	}

	public void setSignaturePartitionChannel(String signaturePartitionChannel) {
		this.signaturePartitionChannel = signaturePartitionChannel;
	}

}
