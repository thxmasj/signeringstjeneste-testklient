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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public void doSignature(SignatureJob signatureJob)  {
		signatureRepository.save(signatureJob);
	}

	public SignatureJob getSignature(Long id) {
		return signatureRepository.findOne(id);

	}

	public List<SignatureJob> getAllSignatures() {
		return signatureRepository.findAll();
	}


	public Page<SignatureJob> getSignatures(int pageNumber) {
		PageRequest pageRequest = buildPageRequest(pageNumber);
		Page<Object[]> rawSignaturePage = signatureRepository.list(pageRequest);
		Page<SignatureJob> SignaturePage = toSignaturePage(rawSignaturePage, pageRequest);
		return SignaturePage;
	}


	private PageRequest buildPageRequest(int pageNumber) {
		return new PageRequest(pageNumber, NUMBER_OF_SIGNATURES_PER_PAGE);
	}

	private Page<SignatureJob> toSignaturePage(Page<Object[]> rawSignaturePage, PageRequest pageRequest) {
		List<SignatureJob> signatureJobs = new ArrayList<SignatureJob>();
		for (Object[] rawSignature : rawSignaturePage.getContent()) {
			SignatureJob SignatureJob = convertToSignature(rawSignature);
			signatureJobs.add(SignatureJob);
		}
		return new PageImpl<SignatureJob>(signatureJobs, pageRequest, rawSignaturePage.getTotalElements());
	}

	private SignatureJob convertToSignature(Object[] rawSignature) {
		// Refer to SignatureRepository.list() for field order
		SignatureJob SignatureJob = new SignatureJob();
		SignatureJob.setId((Long) rawSignature[0]);
		SignatureJob.setSsn((String) rawSignature[1]);
		Document document = new Document();
		document.setTitle((String) rawSignature[2]);
		SignatureJob.setDocument(document);
		return SignatureJob;
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
