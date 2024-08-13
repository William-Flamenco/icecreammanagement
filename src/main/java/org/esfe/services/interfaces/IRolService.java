package org.esfe.services.interfaces;

import org.esfe.models.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    Page<Rol> findAll(Pageable pageable);

    List<Rol> getAll();

    Optional<Rol> findOneById(Integer rolId);

    Rol createOrEditOne(Rol rol);

    void deleteOneById(Integer rolId);
}
