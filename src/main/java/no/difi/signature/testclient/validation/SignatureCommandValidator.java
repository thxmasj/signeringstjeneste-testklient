package no.difi.signature.testclient.validation;

import no.difi.signature.testclient.web.SignatureJobCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * Validator/klasse for SignatureCommand.
 */
@Component("signatureCommandValidator")
public class SignatureCommandValidator implements Validator {

    @Autowired
    @Qualifier("mvcValidator")
    private Validator basicValidator;


    @Override
    public boolean supports(Class<?> aClass) {
        return SignatureJobCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SignatureJobCommand signature = (SignatureJobCommand) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "ssn", "SSN er tomt");
        //basicValidator.validate(signature, errors);


    }
}
