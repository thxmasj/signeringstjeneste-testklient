package no.difi.signature.testclient.web;


import no.difi.signature.testclient.domain.Document;
import no.difi.signature.testclient.domain.Signature;
import no.difi.signature.testclient.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.env.Environment;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@Controller
public class SignatureController {

	@Autowired
	private Environment environment;


	@Autowired
	private SignatureService signatureService;

	static final Logger logger = LoggerFactory.getLogger(SignatureController.class);

	@Autowired
	@Qualifier("signatureCommandValidator")
	private Validator signatureValidator;

	@Autowired
	@Qualifier("mvcValidator")
	private Validator validator;

	@InitBinder
	protected void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.setValidator(validator);
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


	@RequestMapping(method = RequestMethod.GET, value = "/", produces = "text/html")
	public String show_signature_page(Model model, @RequestParam(required = false) Long copy) throws ChangeSetPersister.NotFoundException {
		SignatureCommand signatureCommand = new SignatureCommand();

		model.addAttribute("signatureCommand", signatureCommand);

		return "signature_page";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/signatures")
	public String do_the_signing(@Validated @ModelAttribute("signatureCommand") SignatureCommand signatureCommand, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
		signatureValidator.validate(signatureCommand, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("signatureCommand", signatureCommand);
			//model.addAttribute("keyPairAliases", postklientService.getKeypairAliases());
			model.addAttribute("errors", bindingResult);
			return "signature_page";
		}
		//TODO: DO THE SIGNING....
		Signature sig = new Signature();
		sig.setSsn(signatureCommand.getSsn());
		signatureService.doSignature(sig);

		return "redirect:/client/signatures/" + sig.getId();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/signatures")
	public String show_signature_list_page(Model model, Long pageNumber) {

		/* Page-versjon
		Page<Signature> signaturePage;
		signaturePage = signatureService.getSignatures(pageNumber);
		model.addAttribute("signaturePage", signaturePage);
		*/

		List<Signature> signatures = signatureService.getAllSignatures();

		System.out.println("List all signatures");
		for (int i = 0; i < signatures.size(); i++) {
			System.out.println("SIGNATUR: " + signatures.get(i).toString());
			System.out.println("ID: " + signatures.get(i).getId() + " - SSN: " + signatures.get(i).getSsn());
		}

		return "show_signature_list_page";
	}


	private Document getDocument(SignatureCommand signatureCommand) throws IOException {
		Document document = new Document();
		document.setTitle(signatureCommand.getTitle());
		document.setContent(signatureCommand.getDocument().getBytes());
		document.setFilename(signatureCommand.getDocument().getOriginalFilename());
		document.setMimetype(signatureCommand.getDocument().getContentType());
		return document;
	}

}
