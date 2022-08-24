package ru.azor.simple.web.form.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.azor.simple.web.form.entities.Privilege;
import ru.azor.simple.web.form.repositories.PrivilegeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
    private final PrivilegeRepository privilegeRepository;

    public List<Privilege> getAll() {
        return privilegeRepository.findAll();
    }
}
