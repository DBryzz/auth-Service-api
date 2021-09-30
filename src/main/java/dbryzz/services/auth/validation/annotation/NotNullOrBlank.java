package dbryzz.services.auth.validation.annotation;

import dbryzz.services.auth.validation.validator.NotNullOrBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NotNullOrBlankValidator.class)
public @interface NotNullOrBlank {

    String message() default "{javax.validation.constraints.Pattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
