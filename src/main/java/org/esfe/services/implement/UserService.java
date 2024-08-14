package org.esfe.services.implement;

import org.esfe.models.Rol;
import org.esfe.models.User;
import org.esfe.repository.UserRepository;
import org.esfe.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    /* Por si acaso, la paginación da problemas, colocarlo como lista.
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

*/

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOneById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User createOrEditOne(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteOneById(Integer userId) {
        userRepository.deleteById(userId);
    }
}
