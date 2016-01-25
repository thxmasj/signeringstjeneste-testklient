package no.difi.signature.testclient.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class SignatureController {

	@Autowired
	private Environment environment;

	static final Logger logger = LoggerFactory.getLogger(SignatureController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/", produces = "text/html")
	public String show_hello_world(Model model, @RequestParam(required = false) Long copy) throws ChangeSetPersister.NotFoundException {
		logger.info("Logback is working");
		return "hello";
	}

}
