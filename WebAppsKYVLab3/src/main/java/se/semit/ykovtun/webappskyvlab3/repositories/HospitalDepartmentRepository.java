package se.semit.ykovtun.webappskyvlab3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    boolean existsByName(String name);
    boolean existsByShortName(String shortname);
}
