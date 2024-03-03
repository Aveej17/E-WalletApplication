package com.Jeeva.UserService.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgeLimitImpl implements ConstraintValidator<AgeLimit, String> {

    private int minimumAge;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
        this.minimumAge = constraintAnnotation.minimumAge();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String DateStr, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(DateStr)){
            return false;
        }
        try {
            Date date  = new SimpleDateFormat("dd/MM/yyyy").parse(DateStr);
            int birthYear = date.getYear();
            Date today = new Date();
            int thisYear = today.getYear();

            int diff = thisYear - birthYear;

            if(diff>=minimumAge){
                return true;
            }

        } catch (ParseException e) {
            return false;
        }
        return false;
    }
}
