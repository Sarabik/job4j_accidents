package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityDataRepository;

@Service
@AllArgsConstructor
public class AuthorityDataService implements AuthorityService {

    private final AuthorityDataRepository authorityDataRepository;

    @Override
    public Authority findByAuthority(String authority) {
        return authorityDataRepository.findByAuthority(authority);
    }
}
