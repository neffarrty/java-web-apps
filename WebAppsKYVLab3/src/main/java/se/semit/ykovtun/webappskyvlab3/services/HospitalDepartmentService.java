package se.semit.ykovtun.webappskyvlab3.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.repositories.HospitalDepartmentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class HospitalDepartmentService {
    private final HospitalDepartmentRepository repository;

    public HospitalDepartment findById(long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hospital department doesn't exist")
        );
    }

    public List<HospitalDepartment> findAll() {
        return this.repository.findAll();
    }

    public HospitalDepartment create(HospitalDepartment department) {
        return this.repository.save(department);
    }

    public HospitalDepartment update(long id, HospitalDepartment department) {
        department.setId(id);
        return this.repository.save(department);
    }

    public void delete(long id) {
        HospitalDepartment department = this.findById(id);
        this.repository.delete(department);
    }
}
