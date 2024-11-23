package se.semit.ykovtun.webappskyvlab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.semit.ykovtun.webappskyvlab4.entities.Patient;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
