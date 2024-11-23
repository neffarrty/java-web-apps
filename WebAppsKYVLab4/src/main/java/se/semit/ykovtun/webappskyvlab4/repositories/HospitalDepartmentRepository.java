package se.semit.ykovtun.webappskyvlab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.semit.ykovtun.webappskyvlab4.entities.HospitalDepartment;

import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    boolean existsByName(String name);
    boolean existsByShortName(String shortname);
    @Query("SELECT DISTINCT hd.codeBuilding FROM HospitalDepartment hd")
    List<String> getAllCodeBuildings();
}
