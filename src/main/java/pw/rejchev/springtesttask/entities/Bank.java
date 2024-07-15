package pw.rejchev.springtesttask.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "banks")
public class Bank {

    @Id
    @Min(0)
    @Max(999999999)
    @Column(name = "bic", length = 10)
    Integer bic;

    @Column(name = "name", unique = true, nullable = false)
    String name;
}
