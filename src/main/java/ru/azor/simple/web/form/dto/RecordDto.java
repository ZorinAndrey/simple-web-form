package ru.azor.simple.web.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.azor.simple.web.form.enums.RecordingDirection;
import ru.azor.simple.web.form.enums.RecordStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель записи клиента")
public class RecordDto {
    @Schema(description = "ID записи", required = true, example = "1")
    private Long id;

    @Schema(description = "Имя клиента", required = true, example = "Иван")
    private String firstname;

    @NotBlank(message = "Фамилия клиента не должна быть пустой")
    @Schema(description = "Фамилия клиента", required = true, example = "Иванов")
    private String lastname;

    @NotNull(message = "Дата рождения клиента не должна быть пустой")
    @Schema(description = "Дата рождения клиента", required = true, example = "11.08.1977")
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @NotNull(message = "Время записи не должно быть пустым")
    @Schema(description = "Время записи", required = true, example = "11.08.2022 12:00")
    @JsonProperty("recording_time")
    private Timestamp recordingTime;
    @NotNull(message = "Направление записи не должно быть пустым")
    @Schema(description = "Направление записи", required = true, example = "DIRECTION_1")
    @JsonProperty("recording_direction")
    private RecordingDirection recordingDirection;

    @NotNull(message = "Должен быть выбран статус")
    @Schema(description = "Статус записи", required = true, example = "ACTIVE")
    private RecordStatus status;

    @Schema(description = "Льготы клиента", required = true, example = "[{1, LEVEL_1}, {2, LEVEL_2}]")
    private Set<PrivilegeDto> privilegesDto;
}
