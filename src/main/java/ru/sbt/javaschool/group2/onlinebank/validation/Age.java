package ru.sbt.javaschool.group2.onlinebank.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
@Documented
public @interface Age {
    String message() default "Возраст должен быть больше {min} лет";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min();
}
