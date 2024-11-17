package se.semit.ykovtun.webappskyvlab3.services.patient;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.repositories.HospitalDepartmentRepository;
import se.semit.ykovtun.webappskyvlab3.repositories.PatientRepository;

import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final HospitalDepartmentRepository departmentRepository;

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(long id) {
        return patientRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient doesn't exist")
        );
    }

    @Override
    public Patient create(Patient patient) {
        if (patient.getDepartment() != null && patient.getDepartment().getId() != null) {
            HospitalDepartment department = departmentRepository
                .findById(patient.getDepartment().getId())
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital department doesn't exist")
                );

            patient.setDepartment(department);
        }

        return patientRepository.save(patient);
    }

    @Override
    public Patient update(long id, Patient patient) {
        findById(id);
        patient.setId(id);
        return patientRepository.save(patient);
    }

    @Override
    public void delete(long id) {
        Patient patient = findById(id);
        this.patientRepository.delete(patient);
    }
}
