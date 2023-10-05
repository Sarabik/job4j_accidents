package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserDataRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDataService implements UserService {

    private final UserDataRepository userDataRepository;

    @Override
    public Optional<User> addUser(User user) {
        try {
            userDataRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
