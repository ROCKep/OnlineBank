package ru.sbt.javaschool.group2.onlinebank.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {

    private int minAge;

    public void initialize(Age age) {
        minAge = age.min();
    }

    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if ( dateOfBirth == null ) {
            return true;
        }
        LocalDate today = LocalDate.now();
        return ChronoUnit.YEARS.between(dateOfBirth, today) >= minAge;
    }
}
