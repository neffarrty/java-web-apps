package se.semit.ykovtun.webappskyvlab2.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;

import se.semit.ykovtun.webappskyvlab2.dao.HospitalDepartmentDAO;
import se.semit.ykovtun.webappskyvlab2.entities.HospitalDepartment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
public class HospitalDepartmentService {
    private final HospitalDepartmentDAO dao = new HospitalDepartmentDAO();

    public static void validateDepartment(HospitalDepartment department) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<HospitalDepartment>> violations = validator.validate(department);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public HospitalDepartment findById(Long id) {
        return this.dao.findById(id);
    }

    public List<HospitalDepartment> findAll() {
        return this.dao.findAll();
    }

    public void save(HospitalDepartment department) {
        validateDepartment(department);

        if (dao.findByKey("name", department.getName()) != null) {
            throw new IllegalArgumentException("Name already exists");
        }
        if (dao.findByKey("shortName", department.getShortName()) != null) {
            throw new IllegalArgumentException("Short name already exists");
        }

        dao.save(department);
    }

    public void update(Long id, HospitalDepartment department) {
        validateDepartment(department);

        HospitalDepartment hd = this.dao.findById(id);
        if (hd == null) {
            throw new IllegalArgumentException("Department doesn't exists");
        }
        if (dao.findByKey("name", department.getName()) != null
                && !hd.getName().equals(department.getName())) {
            throw new IllegalArgumentException("Name already exists");
        }
        if (dao.findByKey("shortName", department.getShortName()) != null
                && !hd.getShortName().equals(department.getShortName())) {
            throw new IllegalArgumentException("Short name already exists");
        }

        dao.update(id, department);
    }

    public void delete(Long id) {
        if (dao.findById(id) == null) {
            throw new IllegalArgumentException("Department doesn't exists");
        }

        dao.delete(id);
    }

    public List<HospitalDepartment> findByTemplate(HospitalDepartment department) {
        return this.dao.findByTemplate(department);
    }

    public HospitalDepartment findByKey(String key, String value) {
        return this.dao.findByKey(key, value);
    }

    public List<String> getUniqueCodeBuildings() {
        return this.findAll().stream()
                .map(HospitalDepartment::getCodeBuilding)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public HospitalDepartment getDepartmentFromRequest(HttpServletRequest req) {
        String name      = req.getParameter("name");
        String shortName = req.getParameter("shortname");
        String building  = req.getParameter("building");
        Integer floor    = null;
        Integer count    = null;

        if (req.getParameter("floor") != null && !req.getParameter("floor").isEmpty()) {
            floor = Integer.parseInt(req.getParameter("floor"));
        }
        if (req.getParameter("count") != null && !req.getParameter("count").isEmpty()) {
            count = Integer.parseInt(req.getParameter("count"));
        }

        return new HospitalDepartment(name, shortName, building, floor, count);
    }
}
