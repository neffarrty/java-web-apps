package se.semit.ykovtun.webappskyvlab3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.semit.ykovtun.webappskyvlab3.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab3.entities.Patient;
import se.semit.ykovtun.webappskyvlab3.repositories.HospitalDepartmentRepository;
import se.semit.ykovtun.webappskyvlab3.repositories.PatientRepository;
import se.semit.ykovtun.webappskyvlab3.services.department.HospitalDepartmentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalDepartmentServiceTest {
    @Mock
    private HospitalDepartmentRepository departmentRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private HospitalDepartmentServiceImpl service;

    @Test
    void shouldFindDepartmentById() {
        HospitalDepartment department = new HospitalDepartment();
        department.setId(1L);
        department.setName("Cardiology");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        HospitalDepartment result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Cardiology", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class, () -> service.findById(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Hospital department not found", exception.getReason());
    }

    @Test
    void shouldFindAllDepartments() {
        List<HospitalDepartment> departments = List.of(new HospitalDepartment(), new HospitalDepartment());
        when(departmentRepository.findAll()).thenReturn(departments);

        List<HospitalDepartment> result = service.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void shouldCreateDepartmentWhenValid() {
        HospitalDepartment department = new HospitalDepartment();
        department.setName("Neurology department");
        department.setShortName("Neurology");

        when(departmentRepository.existsByName("Neurology department")).thenReturn(false);
        when(departmentRepository.existsByShortName("Neurology")).thenReturn(false);
        when(departmentRepository.save(department)).thenReturn(department);

        HospitalDepartment result = service.create(department);

        assertNotNull(result);
        assertEquals("Neurology department", result.getName());
        assertEquals("Neurology", result.getShortName());
    }

    @Test
    void shouldThrowExceptionWhenDuplicate() {
        HospitalDepartment department = new HospitalDepartment();
        department.setName("Neurology department");
        department.setShortName("Neurology");

        when(departmentRepository.existsByName("Neurology department")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> service.create(department)
        );

        assertEquals("Department already exists", exception.getMessage());
    }

    @Test
    void shouldDeleteDepartmentById() {
        HospitalDepartment department = new HospitalDepartment();
        department.setId(1L);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).delete(department);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(departmentRepository).delete(department);
    }

    @Test
    void shouldAddPatientToDepartment() {
        HospitalDepartment department = new HospitalDepartment();
        department.setId(1L);
        department.setBoxCount(10);

        Patient patient = new Patient();
        patient.setRoom(5);

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(patientRepository.save(patient)).thenReturn(patient);

        assertDoesNotThrow(() -> service.addPatient(1L, patient));
        verify(patientRepository).save(patient);
    }

    @Test
    void shouldGetAllCodeBuildings() {
        List<String> codes = List.of("A-12", "B-34");
        when(departmentRepository.getAllCodeBuildings()).thenReturn(codes);

        List<String> result = service.getAllCodeBuildings();

        assertEquals(2, result.size());
        assertTrue(result.contains("A-12"));
        assertTrue(result.contains("B-34"));
    }
}

