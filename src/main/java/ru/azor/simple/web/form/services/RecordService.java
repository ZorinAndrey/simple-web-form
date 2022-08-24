package ru.azor.simple.web.form.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import ru.azor.simple.web.form.converters.RecordConverter;
import ru.azor.simple.web.form.dto.RecordDto;
import ru.azor.simple.web.form.entities.Record;
import ru.azor.simple.web.form.enums.RecordingDirection;
import ru.azor.simple.web.form.exceptions.ClientException;
import ru.azor.simple.web.form.exceptions.ValidationException;
import ru.azor.simple.web.form.repositories.RecordRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final RecordConverter recordConverter;

    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    public Optional<Record> findById(Long id) {
        if (id == null) {
            log.error("Find by id: id = null");
            return Optional.empty();
        }
        Optional<Record> optionalRecord = recordRepository.findById(id);
        log.info("Find by id: id = " + id);
        return optionalRecord;
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new ClientException("Невалидный параметр, идентификатор:" + null, HttpStatus.BAD_REQUEST);
        }
        try {
            recordRepository.deleteById(id);
            log.info("Deleted: id = " + id);
        } catch (Exception ex) {
            throw new ClientException("Ошибка удаления записи. Запись " + id + "не существует", HttpStatus.NOT_FOUND);
        }
    }

    private Record save(Record record) {
        if (record == null) {
            throw new ClientException("Невалидный параметр 'record': null", HttpStatus.BAD_REQUEST);
        }
        if (record.getId() == null && isRecordingTimeAndRecordingDirectionPresent(record.getRecordingTime(), record.getRecordingDirection())) {
            throw new ClientException("Запись с такими направлением и временем уже существует:", HttpStatus.CONFLICT);
        }
        Record savedRecord = recordRepository.save(record);
        log.info("Saved: " + savedRecord.getLastname());
        return savedRecord;
    }

    private boolean isRecordingTimeAndRecordingDirectionPresent(Timestamp recordingTime, RecordingDirection recordingDirection) {
        return recordRepository.countRecordingTimeAndRecordDirectionPresent(recordingTime, recordingDirection) > 0;
    }

    public Record tryToSave(RecordDto recordDto, BindingResult bindingResult) {
        if (recordDto == null) {
            throw new ClientException("Невалидный параметр 'recordDto': null", HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            throw new ValidationException("Ошибка валидации", errors, HttpStatus.BAD_REQUEST);
        }
        return save(recordConverter.dtoToEntity(recordDto));
    }

}
