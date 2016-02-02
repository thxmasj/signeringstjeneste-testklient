package no.difi.signature.testclient.web;

import no.digipost.signature.client.core.ConfirmationReference;
import no.digipost.signature.client.core.Document;
import no.digipost.signature.client.core.PAdESReference;
import no.digipost.signature.client.core.Signer;
import no.digipost.signature.client.core.XAdESReference;
import no.digipost.signature.client.direct.DirectClient;
import no.digipost.signature.client.direct.DirectJob;
import no.digipost.signature.client.direct.DirectJobResponse;
import no.digipost.signature.client.direct.DirectJobStatusResponse;
import no.digipost.signature.client.direct.StatusReference;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
public class DirectClientController {

    @Autowired
    private DirectClient directClient;

    @RequestMapping(path = "/jobs/{signerSsn}", method = RequestMethod.POST)
    public DirectJobResponse createJob(
            @PathVariable String signerSsn,
            HttpServletRequest servletRequest
    ) throws IOException {
        Document document = Document.builder(
                "TestSubject",
                "TestMessage",
                "TestFileName",
                IOUtils.toByteArray(getClass().getResourceAsStream("/SDP-Vedlegg1-2MB.pdf"))
        ).build();
        DirectJob job = DirectJob.builder(
                new Signer(signerSsn),
                document,
                completedUrl(servletRequest),
                cancelledUrl(servletRequest),
                errorUrl(servletRequest)
        ).build();
        return directClient.create(job);
    }

    @RequestMapping(path = "/jobs/status/{statusUrl}")
    public DirectJobStatusResponse getStatus(@PathVariable String statusUrl) throws UnsupportedEncodingException {
        return directClient.getStatus(
                new StatusReference(fromBase64(statusUrl))
        );
    }

    @RequestMapping(path = "/jobs/document/pades/{documentUrl}")
    public byte[] getPadesDocument(@PathVariable String documentUrl) throws IOException {
        return IOUtils.toByteArray(directClient.getPAdES(
                PAdESReference.of(fromBase64(documentUrl))
        ));
    }

    @RequestMapping(path = "/jobs/document/xades/{documentUrl}")
    public byte[] getXadesDocument(@PathVariable String documentUrl) throws IOException {
        return IOUtils.toByteArray(directClient.getXAdES(
                XAdESReference.of(fromBase64(documentUrl))
        ));
    }

    @RequestMapping(path = "/jobs/status/{confirmationUrl}", method = RequestMethod.POST)
    public void confirmFinished(@PathVariable String confirmationUrl) throws UnsupportedEncodingException {
        directClient.confirm(new DirectJobStatusResponse(
                0, null, ConfirmationReference.of(fromBase64(confirmationUrl)), null, null)
        );
    }

    private String completedUrl(HttpServletRequest servletRequest) {
        return servletRequest.getRequestURL().append("/completed").toString();
    }

    private String cancelledUrl(HttpServletRequest servletRequest) {
        return servletRequest.getRequestURL().append("/cancelled").toString();
    }

    private String errorUrl(HttpServletRequest servletRequest) {
        return servletRequest.getRequestURL().append("/error").toString();
    }

    private String fromBase64(String s) throws UnsupportedEncodingException {
        return new String(Base64.getUrlDecoder().decode(s), "UTF-8");
    }

}
