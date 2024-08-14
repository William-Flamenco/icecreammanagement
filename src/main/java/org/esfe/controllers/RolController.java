package org.esfe.controllers;

import org.esfe.models.Rol;
import org.esfe.services.interfaces.IRolService;
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
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("user", rolService.findAll());
        return "rol/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Rol rol = rolService.findOneById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        model.addAttribute("rol", rol);
        return "rol/details";
    }

    @GetMapping("/create")
    public String create(Rol rol){
        return "rol/create";
    }

    @PostMapping("/save")
    public String save(Rol rol, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(rol);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "rol/create";
        }

        rolService.createOrEditOne(rol);
        attributes.addFlashAttribute("msg", "Rol creado correctamente");
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Rol rol = rolService.findOneById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        model.addAttribute("rol", rol);
        return "rol/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Rol rol = rolService.findOneById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        model.addAttribute("rol", rol);
        return "rol/delete";
    }


    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id, RedirectAttributes attributes) {
        Optional<Rol> rolOpt = rolService.findOneById(id);
        if (rolOpt.isPresent()) {
            Rol rol = rolOpt.get();
            // Verificar si el rol tiene usuarios asociados
            if (rol.getUsers().isEmpty()) {
                // Eliminar el rol si no tiene usuarios asociados
                rolService.deleteOneById(id);
                attributes.addFlashAttribute("msg2", "Rol eliminado correctamente");
            } else {
                // Si hay usuarios asociados, mostrar un mensaje de error
                attributes.addFlashAttribute("error", "No se puede eliminar el rol porque está asociado a uno o más usuarios.");
            }
        } else {
            attributes.addFlashAttribute("error", "Rol no encontrado");
        }
        return "redirect:/roles";
    }

}