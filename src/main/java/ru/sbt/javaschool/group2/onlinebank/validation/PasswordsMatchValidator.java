package ru.sbt.javaschool.group2.onlinebank.validation;

import ru.sbt.javaschool.group2.onlinebank.client.ClientDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

   public void initialize(PasswordsMatch constraint) {
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {
       ClientDto clientDto = (ClientDto) obj;
       return Objects.equals(clientDto.getPassword(), clientDto.getMatchingPassword());
   }
}
