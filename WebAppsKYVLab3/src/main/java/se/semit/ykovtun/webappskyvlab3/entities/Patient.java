package se.semit.ykovtun.webappskyvlab3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import se.semit.ykovtun.webappskyvlab3.annotations.ValidPatientRoom;

import java.time.LocalDateTime;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-11-16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patients")
// @ValidPatientRoom
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(nullable = false)
    @Check(constraints = "REGEXP_LIKE(name, '^[A-Z][a-z]+$','c') = 1")
    @Pattern(
        regexp = "^[A-Z][a-z]+$",
        message = "Invalid name"
    )
    String name;

    @Column(nullable = false)
    @Check(constraints = "REGEXP_LIKE(surname, '^[A-Z][a-z]+$','c') = 1")
    @Pattern(
        regexp = "^[A-Z][a-z]+$",
        message = "Invalid surname"
    )
    String surname;

    @Column(columnDefinition = "int default 0")
    @NotNull(message = "Age must be provided")
    @PositiveOrZero(
        message = "Age must be positive"
    )
    Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    HospitalDepartment department;

    @Column(nullable = false)
    @NotNull(message = "Arrival time must be provided")
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime arrival = LocalDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Room number must be provided")
    Integer room;

    public String getFullName() {
        return name + " " + surname;
    }
}
