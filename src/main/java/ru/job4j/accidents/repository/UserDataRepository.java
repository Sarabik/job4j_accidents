package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accidents.model.User;

public interface UserDataRepository  extends CrudRepository<User, Integer> {

    @Query("FROM User WHERE username = :name")
    User getUserByUsername(@Param("name") String name);

}
