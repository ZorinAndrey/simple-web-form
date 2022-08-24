package ru.azor.simple.web.form.converters;

import org.springframework.stereotype.Component;
import ru.azor.simple.web.form.dto.PrivilegeDto;
import ru.azor.simple.web.form.entities.Privilege;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PrivilegeConverter {
    public Set<Privilege> setDtoToSetEntity(Set<PrivilegeDto> privilegesDto) {
        return privilegesDto.stream().map(this::dtoToEntity).collect(Collectors.toSet());
    }

    public Set<PrivilegeDto> setEntityToSetDto(Set<Privilege> privileges) {
        return privileges.stream().map(this::entityToDto).collect(Collectors.toSet());
    }

    private Privilege dtoToEntity(PrivilegeDto privilegeDto) {
        return Privilege.builder()
                .id(privilegeDto.getId())
                .name(privilegeDto.getName())
                .build();
    }

    public PrivilegeDto entityToDto(Privilege privilege) {
        return PrivilegeDto.builder()
                .id(privilege.getId())
                .name(privilege.getName())
                .build();
    }
}
