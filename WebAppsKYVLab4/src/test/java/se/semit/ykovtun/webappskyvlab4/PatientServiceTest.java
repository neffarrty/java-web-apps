package se.semit.ykovtun.webappskyvlab4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.semit.ykovtun.webappskyvlab4.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab4.entities.Patient;
import se.semit.ykovtun.webappskyvlab4.repositories.PatientRepository;
import se.semit.ykovtun.webappskyvlab4.services.patient.PatientServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientServiceImpl service;

    private Patient patient;

    @BeforeEach
    void setUp() {
        HospitalDepartment department = new HospitalDepartment();
        department.setId(1L);
        department.setName("Cardiology Department");
        department.setShortName("Cardiology");
        department.setBoxCount(10);

        patient = new Patient(0L, "John", "Doe", 30, department, LocalDateTime.now(), 5);
    }

    @Test
    void shouldReturnAllPatients() {
        when(repository.findAll()).thenReturn(List.of(patient));

        List<Patient> patients = service.findAll();

        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldReturnPatientByIdWhenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        Patient patient = service.findById(1L);

        assertEquals("John", patient.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFoundById() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class, () -> service.findById(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Patient not found", exception.getReason());
    }

    @Test
    void shouldSaveAndReturnPatientWhenValid() {
        when(repository.save(patient)).thenReturn(patient);

        Patient createdPatient = service.create(patient);

        assertEquals("John", createdPatient.getName());
        verify(repository, times(1)).save(patient);
    }

    @Test
    void shouldUpdateAndReturnPatientWhenValid() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));
        when(repository.save(patient)).thenReturn(patient);

        Patient updatedPatient = service.update(1L, patient);

        assertEquals("John", updatedPatient.getName());
        verify(repository, times(1)).save(patient);
    }

    @Test
    void shouldDeletePatientWhenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        service.delete(1L);

        verify(repository, times(1)).delete(patient);
    }

    @Test
    void shouldThrowExceptionWhenRoomExceedsCapacity() {
        patient.setRoom(15);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> service.create(patient)
        );

        assertEquals("Room number cannot exceed 10", exception.getMessage());
    }
}
