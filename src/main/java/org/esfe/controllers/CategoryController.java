package org.esfe.controllers;

import jakarta.validation.Valid;
import org.esfe.models.Category;
import org.esfe.models.User;
import org.esfe.services.implement.CategoryService;
import org.esfe.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Category> categories = categoryService.findAll(pageable);
        model.addAttribute("categories", categories);

        int totalPages = categories.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "categories/index";
    }

    @GetMapping("/details/{id}")
    public String details (@PathVariable("id") Integer id, Model model){
        Category category = categoryService.findOneById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrado"));
        model.addAttribute("category", category);
        return "category/details";
    }

    @GetMapping("/create")
    public String create(Category category, Model model){
        model.addAttribute("roles", categoryService.findAll());
        return "category/create";
    }

    @PostMapping("/save")
    public String save(@Valid Category category, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.findAll());
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "category/create";
    }
        categoryService.createOrEditOne(category);
        attributes.addFlashAttribute("msg", "Categoria creadoa correctamente");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Category category = categoryService.findOneById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        model.addAttribute("user", category);
        model.addAttribute("roles", categoryService.findAll());
        return "category/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.findOneById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        model.addAttribute("category", category);
        return "category/delete";
    }

    @GetMapping("/delete")
    public String delete(Category category, RedirectAttributes attributes){
        categoryService.deleteOneById(category.getId());
        attributes.addFlashAttribute("msg2", "Categoria eliminada correctamente");
        return "redirect:/categories";
    }
}




