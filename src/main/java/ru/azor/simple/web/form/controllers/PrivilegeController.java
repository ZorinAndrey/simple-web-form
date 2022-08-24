package ru.azor.simple.web.form.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.azor.simple.web.form.converters.PrivilegeConverter;
import ru.azor.simple.web.form.dto.PrivilegeDto;
import ru.azor.simple.web.form.exceptions.AppError;
import ru.azor.simple.web.form.services.PrivilegeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/privileges")
@RequiredArgsConstructor
@Tag(name = "Льготы", description = "Методы работы со льготами")
public class PrivilegeController {
    private final PrivilegeService privilegeService;
    private final PrivilegeConverter privilegeConverter;

    @Operation(
            summary = "Запрос на получение списка льгот",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка", responseCode = "4XX",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping
    public List<PrivilegeDto> getAll() {
        return privilegeService.getAll().stream().map(privilegeConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
