package no.difi.signature.testclient.web;


import no.difi.signature.testclient.domain.Document;
import no.difi.signature.testclient.domain.SignatureJob;
import no.difi.signature.testclient.service.SignatureService;
import no.digipost.signature.client.direct.DirectJobResponse;
import no.digipost.signature.client.direct.DirectJobStatusResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


@Controller
public class SignatureController {

	@Autowired
	private SignatureService signatureService;

	@Autowired
	@Qualifier("signatureCommandValidator")
	private Validator signatureValidator;

	@Autowired
	@Qualifier("mvcValidator")
	private Validator validator;

	@Autowired
	private DirectClientController directClient;

	@Autowired
	private HttpSession session;

	@InitBinder
	protected void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(validator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


	@RequestMapping(method = RequestMethod.GET, value = "/", produces = "text/html")
	public String show_signature_page(Model model) throws ChangeSetPersister.NotFoundException {
		SignatureCommand signatureCommand = new SignatureCommand();
		model.addAttribute("signatureCommand", signatureCommand);
		return "signature_page";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/signatures")
	public String do_the_signing(@Validated @ModelAttribute("signatureCommand") SignatureCommand signatureCommand, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
		signatureValidator.validate(signatureCommand, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("signatureCommand", signatureCommand);
			model.addAttribute("errors", bindingResult);
			return "signature_page";
		}
		DirectJobResponse response = directClient.createJob(signatureCommand.getSsn(), request);
		SignatureJob sig = new SignatureJob();
		sig.setSsn(signatureCommand.getSsn());
		sig.setDocument(getDocument(signatureCommand));
		sig.setTitle((signatureCommand.getTitle()));
		sig.setInsensitiveTitle(signatureCommand.getInsensitiveTitle());
		sig.setStatusUrl(Base64.getUrlEncoder().encodeToString(response.getStatusUrl().getStatusUrl().getBytes("UTF-8")));
		signatureService.doSignature(sig);
		session.setAttribute("signatureJobId", sig.getId());
		return "redirect:" + response.getRedirectUrl();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/signatures/completed")
	public String getSignedDocument() throws IOException {
		SignatureJob job = signatureService.getSignature((Long)session.getAttribute("signatureJobId"));
		DirectJobStatusResponse response = directClient.getStatus(job.getStatusUrl());
		byte[] padesDocument = directClient.getPadesDocument(base64(response.getpAdESUrl().getpAdESUrl()));
		Document signedDocument = Document.builder()
				.content(padesDocument)
				.mimeType("application/pdf")
				.build();
		job.setSignedDocument(signedDocument);
		signatureService.doSignature(job);
		directClient.confirmFinished(base64(response.getConfirmationReference().getConfirmationUrl()));
		return "redirect:/client/signatures/" + job.getId();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/signatures/{id}")
	public String show_message_page(@PathVariable Long id, Model model) throws ChangeSetPersister.NotFoundException {
		SignatureJob sig = signatureService.getSignature(id);
		if (sig == null) {
			throw new ChangeSetPersister.NotFoundException();
		}
		model.addAttribute("signatureJob", sig);
		return "processed_signature";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/signatures")
	public String show_signature_list_page(Model model, @RequestParam(defaultValue = "0") int pageNumber) {

		Page<SignatureJob> signaturePage;
		signaturePage = signatureService.getSignatures(pageNumber);
		model.addAttribute("signaturePage", signaturePage);

		return "show_signature_list_page";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/signatures/documents/{id}")
	public void download_Signature_document(@PathVariable Long id, HttpServletResponse response) throws ChangeSetPersister.NotFoundException, IOException {
		Document document = signatureService.getDocument(id);
		if (document == null || document.getContent() == null) {
			throw new ChangeSetPersister.NotFoundException();
		}
		response.addHeader("Content-Disposition", "attachment; filename=\"" + document.getFilename() + "\"");
		response.setContentType(document.getMimetype());
		InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(document.getContent()));
		IOUtils.copy(inputStream, response.getOutputStream());
	}

	private Document getDocument(SignatureCommand signatureCommand) throws IOException {
		return Document.builder()
				.title(signatureCommand.getTitle())
				.content(signatureCommand.getDocument().getBytes())
				.fileName(signatureCommand.getDocument().getOriginalFilename())
				.mimeType(signatureCommand.getDocument().getContentType())
				.build();
	}

	private String base64(String s) throws UnsupportedEncodingException {
		return Base64.getUrlEncoder().encodeToString(s.getBytes("UTF-8"));
	}


}
