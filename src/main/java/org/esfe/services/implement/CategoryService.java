package org.esfe.services.implement;


import org.esfe.models.Category;
import org.esfe.repository.CategoryRepository;
import org.esfe.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findOneById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOrEditOne(Category rol) {
        return categoryRepository.save(rol);
    }

    @Override
    public void deleteOneById(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
}

}
