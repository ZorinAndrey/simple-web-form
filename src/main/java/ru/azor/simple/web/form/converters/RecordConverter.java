package ru.azor.simple.web.form.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.azor.simple.web.form.dto.RecordDto;
import ru.azor.simple.web.form.entities.Record;

@Component
@RequiredArgsConstructor
public class RecordConverter {
    private final PrivilegeConverter privilegeConverter;

    public Record dtoToEntity(RecordDto recordDto) {
        return Record.builder()
                .id(recordDto.getId())
                .firstname(recordDto.getFirstname())
                .lastname(recordDto.getLastname())
                .dateOfBirth(recordDto.getDateOfBirth())
                .recordingTime(recordDto.getRecordingTime())
                .recordingDirection(recordDto.getRecordingDirection())
                .status(recordDto.getStatus())
                .privileges(privilegeConverter.setDtoToSetEntity(recordDto.getPrivilegesDto()))
                .build();
    }

    public RecordDto entityToDto(Record record) {
        return RecordDto.builder()
                .id(record.getId())
                .firstname(record.getFirstname())
                .lastname(record.getLastname())
                .dateOfBirth(record.getDateOfBirth())
                .recordingTime(record.getRecordingTime())
                .recordingDirection(record.getRecordingDirection())
                .status(record.getStatus())
                .privilegesDto(privilegeConverter.setEntityToSetDto(record.getPrivileges()))
                .build();
    }
}
