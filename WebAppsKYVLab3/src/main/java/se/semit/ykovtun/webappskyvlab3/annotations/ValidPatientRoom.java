package se.semit.ykovtun.webappskyvlab3.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import se.semit.ykovtun.webappskyvlab3.validators.PatientRoomValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PatientRoomValidator.class)
@Target({ ElementType.TYPE }) // Применяется к классам
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPatientRoom {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
