package se.semit.ykovtun.webappskyvlab3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

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

    @NotNull(message = "Age must be provided")
    @Positive(message = "Age must be positive")
    @Check(constraints = "age > 0")
    Integer age;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    HospitalDepartment department;

    @Column(nullable = false)
    @NotNull(message = "Arrival time must be provided")
    @PastOrPresent
    @Check(constraints = "arrival <= CURRENT_TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime arrival = LocalDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Room number must be provided")
    @Positive(message = "Room number must be positive")
    @Check(constraints = "room > 0")
    Integer room;

    public String getFullName() {
        return name + " " + surname;
    }
}
