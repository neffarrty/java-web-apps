import org.junit.jupiter.api.*;
import se.semit.ykovtun.webappskyvlab2.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab2.services.HospitalDepartmentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HospitalDepartmentServiceTest {
    private static HospitalDepartmentService service;
    private static HospitalDepartment department;
    private static HospitalDepartment duplicate;

    @BeforeAll
    public static void setup() {
        service = new HospitalDepartmentService();
        department = new HospitalDepartment(
            "Cardiac surgery department for heart surgeries",
            "Cardiac", "A-01", 2, 18
        );
        duplicate = new HospitalDepartment(
            "Cardiac surgery department for heart surgeries",
            "CD", "B-02", 3, 14
        );
    }

    @Test
    @Order(1)
    void saveTest() {
        service.save(department);
        assertNotNull(department.getId());
    }

    @Test
    @Order(2)
    void saveDuplicateTest() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(duplicate);
        });
        assertEquals("Name already exists", exception.getMessage());
    }

    @Test
    @Order(3)
    void updateTest() {
        department.setBoxCount(21);
        service.update(department.getId(), department);
        HospitalDepartment expected = service.findById(department.getId());
        assertEquals(expected.getName(), department.getName());
    }

    @Test
    @Order(4)
    void updateDuplicateTest() {
        department.setShortName("Cardiology");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.update(department.getId(), department);
        });
        assertEquals("Short name already exists", exception.getMessage());
        department.setShortName("Cardiac");
    }

    @Test
    @Order(5)
    void findByIdTest() {
        HospitalDepartment expected = service.findById(department.getId());
        assertNotNull(expected);
        assertEquals(expected.getName(), department.getName());
    }

    @Test
    @Order(6)
    void findAllTest() {
        List<HospitalDepartment> expected = service.findAll();
        assertFalse(expected.isEmpty());
    }

    @Test
    @Order(7)
    void findByKeyTest() {
        HospitalDepartment expected = service.findByKey("name", department.getName());
        assertNotNull(expected);
    }

    @Test
    @Order(8)
    void findByTemplateTest() {
        List<HospitalDepartment> expected = service.findByTemplate(department);
        assertFalse(expected.isEmpty());
    }

    @Test
    @Order(9)
    void deleteTest() {
        service.delete(department.getId());
        HospitalDepartment expected = service.findById(department.getId());
        assertNull(expected);
    }
}
