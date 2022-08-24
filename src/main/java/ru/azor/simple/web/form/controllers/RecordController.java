package ru.azor.simple.web.form.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.azor.simple.web.form.converters.RecordConverter;
import ru.azor.simple.web.form.dto.RecordDto;
import ru.azor.simple.web.form.entities.Record;
import ru.azor.simple.web.form.exceptions.AppError;
import ru.azor.simple.web.form.exceptions.ClientException;
import ru.azor.simple.web.form.services.RecordService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
@Tag(name = "Записи", description = "Методы работы с записями")
public class RecordController {
    private final RecordService recordService;
    private final RecordConverter recordConverter;

    @Operation(
            summary = "Запрос на получение списка записей",
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
    public List<RecordDto> getAll() {
        return recordService.getAll().stream().map(
                recordConverter::entityToDto
        ).collect(Collectors.toList());
    }

    @Operation(
            summary = "Создание записи",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка", responseCode = "4XX",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Parameter(description = "Новая запись", required = true) @Valid RecordDto recordDto,
                                  @Parameter(description = "Ошибки валидации", required = true) BindingResult bindingResult) {
        Record record = recordService.tryToSave(recordDto, bindingResult);
        return new ResponseEntity<>(recordConverter.entityToDto(record), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Запрос на получение записи по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = RecordDto.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка", responseCode = "4XX",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public RecordDto getById(
            @PathVariable @Parameter(description = "Идентификатор записи", required = true) Long id
    ) {
        Record record = recordService.findById(id).orElseThrow(() -> new ClientException("Запись не найдена, id: " + id, HttpStatus.NOT_FOUND));
        return recordConverter.entityToDto(record);
    }

    @Operation(
            summary = "Удаление записи",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Ошибка", responseCode = "4XX",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Parameter(description = "Идентификатор записи", required = true) Long id) {
        recordService.deleteById(id);
    }
}
