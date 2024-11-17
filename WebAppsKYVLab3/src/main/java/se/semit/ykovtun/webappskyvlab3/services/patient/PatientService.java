package se.semit.ykovtun.webappskyvlab3.services.patient;

import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
public interface PatientService {
    List<Patient> findAll();

    Patient findById(long id);

    Patient create(Patient patient);

    Patient update(long id, Patient patient);

    void delete(long id);
}
