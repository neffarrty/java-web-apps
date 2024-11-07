package se.semit.ykovtun.webappskyvlab3.entities;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Check;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hospital_departments")
public class HospitalDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 64)
    @Check(constraints = "REGEXP_LIKE(name, '^[A-Z][a-z]+(?: [a-zA-Z][a-z]+)*$','c') = 1")
    @Pattern(
        regexp = "^[A-Z][a-z]+(?: [a-zA-Z][a-z]+)*$",
        message = "Name should start with a capital letter and contain only letters"
    )
    @Size(max = 64, message = "Name shouldn't be greater than 64 characters")
    private String name;

    @Column(name = "short_name", nullable = false, unique = true, length = 16)
    @Check(constraints = "REGEXP_LIKE(short_name, '^[A-Z][A-Za-z]+$','c') = 1")
    @Pattern(
        regexp = "^[A-Z][A-Za-z]+$",
        message = "Short name should start with a capital letter and contain only letters"
    )
    @Size(max = 16, message = "Short name shouldn't be greater than 16 characters")
    private String shortName;

    @Column(name = "code_building", nullable = false, length = 4)
    @Check(constraints = "REGEXP_LIKE(code_building, '^[A-Z]-[0-9]{2}$','c') = 1")
    @Pattern(
        regexp = "^[A-Z]-[0-9]{2}$",
        message = "Building code must be in the format e.g., A-12"
    )
    private String codeBuilding;

    @Column(nullable = false)
    @Check(constraints = "floor > 0 AND floor <= 15")
    @NotNull(message = "Floor must be provided")
    @Range(
        min = 1, max = 15,
        message = "Floor must be between 1 and 15"
    )
    private Integer floor;

    @Column(name = "box_count", nullable = false)
    @Check(constraints = "box_count > 0 AND box_count <= 50")
    @NotNull(message = "Box count must be provided")
    @Range(
        min = 1, max = 50,
        message = "Box count must be between 1 and 50"
    )
    private Integer boxCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "department")
    // @JsonIgnoreProperties("department")
    // @JsonManagedReference()
    private Set<Patient> patients;
}

