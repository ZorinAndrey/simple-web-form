package ru.azor.simple.web.form.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель льготы пользователя")
public class PrivilegeDto {
    @Schema(description = "ID льготы", required = true, example = "1")
    private Long id;
    @Schema(description = "Название льготы", required = true, example = "LEVEL_1")
    private String name;
}
