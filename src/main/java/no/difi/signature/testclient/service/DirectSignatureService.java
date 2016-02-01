package no.difi.signature.testclient.service;

import no.digipost.signature.client.core.Document;
import no.digipost.signature.client.core.Signer;
import no.digipost.signature.client.direct.DirectClient;
import no.digipost.signature.client.direct.DirectJob;
import no.digipost.signature.client.direct.DirectJobResponse;
import no.digipost.signature.client.direct.DirectJobStatusResponse;
import no.digipost.signature.client.direct.StatusReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectSignatureService {

    private DirectClient signatureClient;

    @Autowired
    public DirectSignatureService(DirectClient signatureClient) {
        this.signatureClient = signatureClient;
    }

    public RedirectUrlAndStatusUrl addSigningJob(String fødselsnummer) {
        Signer signer = new Signer(fødselsnummer);
        Document document = Document.builder("TestSubject", "TestMessage", "TestFileName", new byte[]{0x01}).build();
        DirectJob job = DirectJob.builder(signer, document, "/completed", "/cancelled", "/error").build();
        DirectJobResponse response = signatureClient.create(job);
        return new RedirectUrlAndStatusUrl(response.getRedirectUrl(), response.getStatusUrl().getStatusUrl());
    }

    public String checkStatus(String statusUrl) {
        DirectJobStatusResponse response = signatureClient.getStatus(new StatusReference(statusUrl));
        return response.getStatus().toString();
    }

    public class RedirectUrlAndStatusUrl {
        private String redirectUrl;
        private String statusUrl;

        public RedirectUrlAndStatusUrl(String redirectUrl, String statusUrl) {
            this.redirectUrl = redirectUrl;
            this.statusUrl = statusUrl;
        }

        public String redirectUrl() {
            return redirectUrl;
        }

        public String statusUrl() {
            return statusUrl;
        }
    }

}
