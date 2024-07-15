package pw.rejchev.springtesttask.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    @Id
    @UuidGenerator
    @Column(name = "id")
    String id;

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Column(name = "short_name", unique = true, nullable = false)
    @JsonProperty(value = "short_name")
    String shortName;

    @Column(name = "address", nullable = false)
    String address;

    @Min(1)
    @Max(79999)
    @Column(name = "okopf", nullable = false)
    Integer okopf;
}
