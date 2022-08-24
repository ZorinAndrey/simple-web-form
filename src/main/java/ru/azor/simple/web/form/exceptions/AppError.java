package ru.azor.simple.web.form.exceptions;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Ошибки сервисов")
public class AppError {
    @Schema(description = "Сообщения ошибок сервисов", example = "Запись не найдена")
    private String message;
    @Schema(description = "Список ошибок", example = "{}")
    private List<ObjectError> list;

    public AppError(String message) {
        this.message = message;
    }
}
