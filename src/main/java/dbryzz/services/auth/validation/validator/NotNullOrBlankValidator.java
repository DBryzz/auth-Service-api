package dbryzz.services.auth.validation.validator;

import dbryzz.services.auth.validation.annotation.NotNullOrBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullOrBlankValidator implements ConstraintValidator<NotNullOrBlank, String> {

    @Override
    public void initialize(NotNullOrBlank parameters) {
        //no-op
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }
        if (value.length() == 0) {
            return false;
        }

        boolean isAllWhitespace = value.matches("^\\s*$");
        return !isAllWhitespace;
    }
}
