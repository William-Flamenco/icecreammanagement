package org.esfe.controllers;

import jakarta.validation.Valid;
import org.esfe.models.User;
import org.esfe.services.interfaces.IRolService;
import org.esfe.services.interfaces.IUserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRolService rolService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<User> users = userService.findAll(pageable);
        model.addAttribute("users", users);

        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "user/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        User user = userService.findOneById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "user/details";
    }

    @GetMapping("/create")
    public String create(User user, Model model){
        model.addAttribute("roles", rolService.findAll());
        return "user/create";
    }

    @PostMapping("/save")
    public String save(@Valid User user, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute("roles", rolService.findAll());
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "user/create";
        }

        userService.createOrEditOne(user);
        attributes.addFlashAttribute("msg", "Usuario creado correctamente");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        User user = userService.findOneById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        model.addAttribute("roles", rolService.findAll());
        return "user/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        User user = userService.findOneById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "user/delete";
    }

    @PostMapping("/delete")
    public String delete(User user, RedirectAttributes attributes){
        userService.deleteOneById(user.getId());
        attributes.addFlashAttribute("msg2", "Usuario eliminado correctamente");
        return "redirect:/users";
    }

}


