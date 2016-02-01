package no.difi.signature.testclient.web;

import no.digipost.signature.client.core.Document;
import no.digipost.signature.client.core.Signer;
import no.digipost.signature.client.direct.DirectClient;
import no.digipost.signature.client.direct.DirectJob;
import no.digipost.signature.client.direct.DirectJobResponse;
import no.digipost.signature.client.direct.DirectJobStatusResponse;
import no.digipost.signature.client.direct.StatusReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
public class DirectClientController {

    @Autowired
    private DirectClient directClient;

    @RequestMapping(path = "/jobs/{signerSsn}", method = RequestMethod.POST)
    public DirectJobResponse createJob(@PathVariable String signerSsn) {
        Signer signer = new Signer(signerSsn);
        Document document = Document.builder("TestSubject", "TestMessage", "TestFileName", new byte[]{0x01}).build();
        DirectJob job = DirectJob.builder(signer, document, "/completed", "/cancelled", "/error").build();
        return directClient.create(job);
    }

    @RequestMapping(path = "/jobs/status/{statusUrl}")
    public DirectJobStatusResponse getStatus(@PathVariable String statusUrl) throws UnsupportedEncodingException {
        return directClient.getStatus(
                new StatusReference(new String(Base64.getUrlDecoder().decode(statusUrl), "UTF-8"))
        );
    }

}
