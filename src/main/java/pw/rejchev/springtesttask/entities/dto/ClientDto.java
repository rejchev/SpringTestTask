package pw.rejchev.springtesttask.entities.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {

    String id;

    @Size(min = 1, max = 256)
    String name;

    @Size(min = 1, max = 45)
    String shortName;

    String address;

    @Min(1)
    @Max(79999)
    Short okopf;
}
