package se.semit.ykovtun.webappskyvlab3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}