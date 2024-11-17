package se.semit.ykovtun.webappskyvlab3.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import se.semit.ykovtun.webappskyvlab3.annotations.ValidPatientRoom;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;

public class PatientRoomValidator implements ConstraintValidator<ValidPatientRoom, Patient> {
    @Override
    public boolean isValid(Patient patient, ConstraintValidatorContext context) {
        if (patient.getRoom() > patient.getDepartment().getBoxCount()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
            "Room number cannot be greater than " + patient.getDepartment().getBoxCount()
            ).addPropertyNode("room").addConstraintViolation();
            return false;
        }
        return true;
    }
}
