package se.semit.ykovtun.webappskyvlab3.services.department;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.repositories.HospitalDepartmentRepository;
import se.semit.ykovtun.webappskyvlab3.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Service
@AllArgsConstructor
public class HospitalDepartmentServiceImpl implements HospitalDepartmentService {
    private final HospitalDepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;

    @Override
    public HospitalDepartment findById(long id) {
        return this.departmentRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital department not found")
        );
    }

    @Override
    public List<HospitalDepartment> findAll() {
        return this.departmentRepository.findAll();
    }

    @Override
    public HospitalDepartment create(HospitalDepartment department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Department already exists");
        }
        if (departmentRepository.existsByShortName(department.getShortName())) {
            throw new IllegalArgumentException("Department already exists");
        }
        return departmentRepository.save(department);
    }

    @Override
    public HospitalDepartment update(long id, HospitalDepartment department) {
        HospitalDepartment old = findById(id);

        if (departmentRepository.existsByName(department.getName())
                && !old.getName().equals(department.getName())) {
            throw new IllegalArgumentException("Department already exists");
        }
        if (departmentRepository.existsByShortName(department.getShortName())
                && !old.getShortName().equals(department.getShortName())) {
            throw new IllegalArgumentException("Department already exists");
        }
        department.setId(id);
        return departmentRepository.save(department);
    }

    @Override
    public void delete(long id) {
        HospitalDepartment department = findById(id);
        departmentRepository.delete(department);
    }

    @Override
    public void addPatient(long id, Patient patient) {
        HospitalDepartment department = findById(id);

        if (patient.getRoom() > department.getBoxCount()) {
            throw new IllegalArgumentException("Room number cannot exceed " + department.getBoxCount());
        }

        patient.setId(null);
        patient.setDepartment(department);

        patientRepository.save(patient);
    }

    @Override
    public List<String> getAllCodeBuildings() {
        return departmentRepository.getAllCodeBuildings();
    }
}
