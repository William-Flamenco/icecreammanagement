package org.esfe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;


@Entity
@Table(name="Product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank (message = "La descripción es requerida")
    private String Description;

    @NotBlank (message = "El precipo es requerido")
    private Double Price;

    @ManyToMany
    @JoinColumn (name = "category_id", nullable = false)
    private  Category category;

    @OneToMany(mappedBy = "product")
    private Set<Pay> pays;

    public Product(Integer id) {
        Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public @NotBlank(message = "La descripción es requerida") String getDescription() {
        return Description;
    }

    public void setDescription(@NotBlank(message = "La descripción es requerida") String description) {
        Description = description;
    }

    public @NotBlank(message = "El precipo es requerido") Double getPrice() {
        return Price;
    }

    public void setPrice(@NotBlank(message = "El precipo es requerido") Double price) {
        Price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Pay> getPays() {
        return pays;
    }

    public void setPays(Set<Pay> pays) {
        this.pays = pays;
    }
}
