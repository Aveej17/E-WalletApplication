package com.Jeeva.UserService.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)  // Do control+b // Basically it tells where I want to put my Annotation
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeLimitImpl.class)
public @interface AgeLimit {
    int minimumAge() default 18;
    String message() default "{your age should be greater than 18}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
