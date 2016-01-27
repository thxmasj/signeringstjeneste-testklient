package no.difi.signature.testclient.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SsnConstraint.class)
public @interface Ssn {

	String message() default "Ugyldig fødselsnummer";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}
