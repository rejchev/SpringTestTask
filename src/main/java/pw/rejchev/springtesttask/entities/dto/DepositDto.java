package pw.rejchev.springtesttask.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class DepositDto {

    String id;

    @NonNull
    @JsonProperty(value = "client_id")
    String clientId;

    @NonNull
    @JsonProperty(value = "bic")
    @Size(max = 9)
    String bankId;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    @JsonProperty(value = "created_at")
    LocalDate createdAt;

    @NonNull
    Double rate;

    @NonNull
    @Positive
    @JsonProperty(value = "duration_in_month")
    Integer durationInMonth;
}
