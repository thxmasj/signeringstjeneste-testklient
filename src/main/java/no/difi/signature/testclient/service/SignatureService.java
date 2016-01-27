package no.difi.signature.testclient.service;

import no.difi.signature.testclient.domain.*;
import no.difi.signature.testclient.repository.DocumentRepository;
import no.difi.signature.testclient.repository.SignatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SignatureService {

	private final static Logger LOGGER = LoggerFactory.getLogger(SignatureService.class);

	private final static int NUMBER_OF_SIGNATURES_PER_PAGE = 100;


	@Autowired
	private SignatureRepository signatureRepository;


	@Autowired
	private DocumentRepository documentRepository;


	@Autowired
	private ConfigurationService configurationService;


    public void doSignature(Signature signature)  {
		signatureRepository.save(signature);
	}

	public Signature getSignature(Long id) {
		return signatureRepository.findOne(id);

	}

	public List<Signature> getAllSignatures() {
		return signatureRepository.findAll();
	}



	public Page<Signature> getSignatures(int pageNumber) {
		PageRequest pageRequest = buildPageRequest(pageNumber);
		Page<Object[]> rawSignaturePage = signatureRepository.list(pageRequest);
		Page<Signature> SignaturePage = toSignaturePage(rawSignaturePage, pageRequest);
		return SignaturePage;
	}


	private PageRequest buildPageRequest(int pageNumber) {
		return new PageRequest(pageNumber, NUMBER_OF_SIGNATURES_PER_PAGE);
	}

	private Page<Signature> toSignaturePage(Page<Object[]> rawSignaturePage, PageRequest pageRequest) {
		List<Signature> Signatures = new ArrayList<Signature>();
		for (Object[] rawSignature : rawSignaturePage.getContent()) {
			Signature Signature = convertToSignature(rawSignature);
			Signatures.add(Signature);
		}
		return new PageImpl<Signature>(Signatures, pageRequest, rawSignaturePage.getTotalElements());
	}

	private Signature convertToSignature(Object[] rawSignature) {
		// Refer to SignatureRepository.list() for field order
		Signature Signature = new Signature();
		Signature.setId((Long) rawSignature[0]);
		Signature.setSsn((String) rawSignature[1]);
		Document document = new Document();
		document.setTitle("DUMMY");   //((String) rawSignature[3]);
		Signature.setDocument(document);
		return Signature;
	}

	
    public void deleteSignature(Long id) {
		signatureRepository.delete(id);
	}

	public void deleteAllSignatures() {
		signatureRepository.deleteAll();

	}

	public Document getDocument(Long id) {
		return documentRepository.findOne(id);

	}

	private String getSignaturePartitionChannel() {
		return configurationService.getConfiguration().getSignaturePartitionChannel();
	}



}
