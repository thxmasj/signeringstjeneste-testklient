package no.difi.signature.testclient.validation;

import no.difi.signature.testclient.web.SignatureCommand;
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
        return SignatureCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SignatureCommand signature = (SignatureCommand) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "ssn", "SSN er tomt");
        //basicValidator.validate(signature, errors);


    }
}
