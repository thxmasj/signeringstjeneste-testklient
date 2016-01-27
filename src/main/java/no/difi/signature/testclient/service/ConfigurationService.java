package no.difi.signature.testclient.service;

import no.difi.signature.testclient.domain.Configuration;
import no.difi.signature.testclient.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class ConfigurationService {

	@Autowired
	ConfigurationRepository configurationRepository;

	@PostConstruct // Avoids concurrency issues by initializing at startup
	public Configuration getConfiguration() {
		Configuration configuration = configurationRepository.findOne(1L);
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setId(1L);
			configuration.setSignaturePartitionChannel(UUID.randomUUID().toString());
			configurationRepository.save(configuration);
		}
		return configuration;
	}
	
}
