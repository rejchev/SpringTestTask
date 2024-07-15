package pw.rejchev.springtesttask.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposits")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Deposit {

    @Id
    @UuidGenerator
    @Column(name = "id")
    String id;

    @ManyToOne(targetEntity = Bank.class, optional = false, cascade = CascadeType.ALL)
    Bank bank;

    @ManyToOne(targetEntity = Client.class, optional = false, cascade = CascadeType.ALL)
    Client client;

    @Column(name = "created_at", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @JsonProperty(value = "created_at")
    LocalDate createdAt;

    @Column(name = "rate", nullable = false)
    Double rate;

    @Column(name = "duration_in_month", nullable = false)
    @JsonProperty("duration_in_month")
    Integer durationInMonth;

    @PrePersist
    public void prePersist() {
        setCreatedAt(LocalDate.now());
    }
}