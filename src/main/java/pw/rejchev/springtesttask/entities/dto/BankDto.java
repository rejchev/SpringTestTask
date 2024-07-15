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
public class BankDto {

    @Min(0)
    @Max(99999999)
    Integer bic;

    @Size(min = 1, max = 256)
    String name;
}
