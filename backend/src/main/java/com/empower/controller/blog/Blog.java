package com.empower.controller.blog;

import java.util.Date;

import com.empower.controller.category.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Images field is required")
    @Column(name = "images", columnDefinition = "text[]")
    private String[] images;

    @NotNull(message = "Category field is required")
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @NotNull(message = "Description field is required")
    private String description;

    private int totalPeople = 0;
    private int totalDonation = 0;
    private int totalVisits = 0;
    private boolean isPublished = false;
    private Date createdAt = new Date();

}
