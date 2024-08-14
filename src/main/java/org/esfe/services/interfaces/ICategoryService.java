package org.esfe.services.interfaces;


import org.esfe.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

  Page<Category> findAll(Pageable pageable) ;

  List<Category> getAll();


  List<Category> findAll();

  Optional<Category>  findOneById(Integer rolId);

  Category createOrEditOne (Category category);

  void deleteOneById(Integer categoryId);

}





