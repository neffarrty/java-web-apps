package se.semit.ykovtun.webappskyvlab3.services;

import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;

import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
public interface HospitalDepartmentService {
    HospitalDepartment findById(long id);

    List<HospitalDepartment> findAll();

    HospitalDepartment create(HospitalDepartment department);

    HospitalDepartment update(long id, HospitalDepartment department);

    void delete(long id);

    void addPatient(Long id, Patient patient);
}
