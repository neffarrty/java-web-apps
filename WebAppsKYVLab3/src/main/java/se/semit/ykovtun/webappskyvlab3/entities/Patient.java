package se.semit.ykovtun.webappskyvlab3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "patients")
@Check(constraints = "")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name must be provided")
    String name;

    @Column(nullable = false)
    @NotBlank(message = "Surname must be provided")
    String surname;

    @Column(nullable = false)
    String patronymic;

    @Column(columnDefinition = "int default 0")
    @PositiveOrZero(
        message = "Age must be positive value"
    )
    Integer age;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    // @JsonIgnoreProperties("patients")
    // @JsonIgnore()
    // @JsonBackReference
    HospitalDepartment department;

    @Column(nullable = false)
    @NotNull(message = "Arrival time must be provided")
    @PastOrPresent
    LocalDateTime arrival;

    @Column(nullable = false)
    @NotNull(message = "Number must be provided")
    Integer number;

    public String getFullName() {
        return name + " " + surname + " " + patronymic;
    }
}
