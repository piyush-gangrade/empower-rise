package com.empower.controller.category;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 50, message = "Name must be greater than 3 and lower than 50")
    @Column(unique = true)
    private String name;
    
    private int totalProducts = 0;

    @NotNull(message = "Photo URL is required")
    private String photoURL;
}
