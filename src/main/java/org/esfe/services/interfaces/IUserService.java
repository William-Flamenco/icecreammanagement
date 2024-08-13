package org.esfe.services.interfaces;

import org.esfe.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Page<User> findAll(Pageable pageable);

    List<User> getAll();

    Optional<User> findOneById(Integer userId);

    User createOrEditOne(User user);

    void deleteOneById(Integer userId);
}
